package ms.cuenta_movimiento.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ms.cuenta_movimiento.application.services.AccountService;
import ms.cuenta_movimiento.domain.models.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
@Tag(name = "Cuentas", description = "API para gestión de cuentas bancarias")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las cuentas", description = "Obtiene la lista de todas las cuentas registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente")
    })
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cuenta por ID", description = "Obtiene una cuenta específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Account> getAccountById(
            @Parameter(description = "ID de la cuenta", required = true) @PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{accountNumber}")
    @Operation(summary = "Obtener cuenta por número", description = "Obtiene una cuenta por su número de cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Account> getAccountByNumber(
            @Parameter(description = "Número de cuenta", required = true) @PathVariable String accountNumber) {
        Optional<Account> account = accountService.getAccountByNumber(accountNumber);
        return account.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clientId}")
    @Operation(summary = "Obtener cuentas de cliente", description = "Obtiene todas las cuentas de un cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuentas del cliente obtenidas exitosamente")
    })
    public ResponseEntity<List<Account>> getAccountsByClientId(
            @Parameter(description = "ID del cliente", required = true) @PathVariable Long clientId) {
        List<Account> accounts = accountService.getAccountsByClientId(clientId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    @Operation(summary = "Crear cuenta", description = "Crea una nueva cuenta bancaria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Account> createAccount(
            @Parameter(description = "Datos de la cuenta a crear", required = true) @RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cuenta", description = "Actualiza los datos de una cuenta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    public ResponseEntity<Account> updateAccount(
            @Parameter(description = "ID de la cuenta", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la cuenta", required = true) @RequestBody Account account) {
        account.setId(id);
        Optional<Account> updatedAccount = accountService.updateAccount(account);
        return updatedAccount.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }
}