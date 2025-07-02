package ms.cuenta_movimiento.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ms.cuenta_movimiento.application.services.MovementService;
import ms.cuenta_movimiento.domain.models.Movement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
@Tag(name = "Movimientos", description = "API para gestión de movimientos bancarios")
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los movimientos", description = "Obtiene la lista de todos los movimientos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida exitosamente")
    })
    public ResponseEntity<List<Movement>> getAllMovements() {
        List<Movement> movements = movementService.getAllMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener movimiento por ID", description = "Obtiene un movimiento específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
        @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<Movement> getMovementById(
            @Parameter(description = "ID del movimiento", required = true) @PathVariable Long id) {
        Optional<Movement> movement = movementService.getMovementById(id);
        return movement.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cuenta/{accountId}")
    public ResponseEntity<List<Movement>> getMovementsByAccountId(@PathVariable Long accountId) {
        List<Movement> movements = movementService.getMovementsByAccountId(accountId);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/cuenta/{accountId}/fechas")
    public ResponseEntity<List<Movement>> getMovementsByAccountIdAndDateRange(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate) {
        
        List<Movement> movements = movementService.getMovementsByAccountIdAndDateRange(accountId, startDate, endDate);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/cliente/{clientId}/fechas")
    public ResponseEntity<List<Movement>> getMovementsByClientIdAndDateRange(
            @PathVariable Long clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate) {
        
        List<Movement> movements = movementService.getMovementsByClientIdAndDateRange(clientId, startDate, endDate);
        return ResponseEntity.ok(movements);
    }

    @PostMapping
    @Operation(summary = "Crear movimiento", description = "Registra un nuevo movimiento bancario (depósito o retiro)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Movimiento creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Saldo no disponible o datos inválidos")
    })
    public ResponseEntity<Movement> createMovement(
            @Parameter(description = "Datos del movimiento a registrar", required = true) @RequestBody Movement movement) {
        Movement createdMovement = movementService.createMovement(movement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovement);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar movimiento", description = "Actualiza los datos de un movimiento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Movimiento actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Movimiento no encontrado")
    })
    public ResponseEntity<Movement> updateMovement(
            @Parameter(description = "ID del movimiento", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos del movimiento", required = true) @RequestBody Movement movement) {
        movement.setId(id);
        Optional<Movement> updatedMovement = movementService.updateMovement(movement);
        return updatedMovement.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }
}