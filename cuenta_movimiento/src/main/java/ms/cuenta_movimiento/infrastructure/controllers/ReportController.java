package ms.cuenta_movimiento.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ms.cuenta_movimiento.application.services.ReportService;
import ms.cuenta_movimiento.domain.models.AccountStatement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "API para generación de reportes bancarios")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    @Operation(summary = "Generar estado de cuenta", description = "Genera un reporte de estado de cuenta para un cliente en un rango de fechas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<List<AccountStatement>> generateAccountStatement(
            @Parameter(description = "ID del cliente", required = true) @RequestParam Long cliente,
            @Parameter(description = "Fecha de inicio (yyyy-MM-dd)", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin (yyyy-MM-dd)", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        
        List<AccountStatement> statements = reportService.generateAccountStatement(cliente, fechaInicio, fechaFin);
        return ResponseEntity.ok(statements);
    }

    @GetMapping("/flexible")
    @Operation(summary = "Generar estado de cuenta (formato flexible)", description = "Genera un reporte de estado de cuenta con formato de fecha flexible")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<List<AccountStatement>> generateAccountStatementFlexible(
            @Parameter(description = "ID del cliente", required = true) @RequestParam Long cliente,
            @Parameter(description = "Fecha de inicio (formato flexible)", required = true) @RequestParam String fechaInicio,
            @Parameter(description = "Fecha de fin (formato flexible)", required = true) @RequestParam String fechaFin) {
        
        LocalDate startDate = parseFlexibleDate(fechaInicio);
        LocalDate endDate = parseFlexibleDate(fechaFin);
        
        List<AccountStatement> statements = reportService.generateAccountStatement(cliente, startDate, endDate);
        return ResponseEntity.ok(statements);
    }
    
    private LocalDate parseFlexibleDate(String dateStr) {
        if (dateStr.contains("T")) {
            return LocalDate.parse(dateStr);
        } else {
            return LocalDate.parse(dateStr + "T00:00:00");
        }
    }
}