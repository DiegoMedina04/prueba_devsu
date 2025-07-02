package ms.cuenta_movimiento.application.services;

import ms.cuenta_movimiento.domain.models.AccountStatement;
import ms.cuenta_movimiento.domain.ports.in.report.GenerateReportUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final GenerateReportUseCase generateReportUseCase;

    public ReportService(GenerateReportUseCase generateReportUseCase) {
        this.generateReportUseCase = generateReportUseCase;
    }

    public List<AccountStatement> generateAccountStatement(Long clientId, LocalDate startDate, LocalDate endDate) {
        return generateReportUseCase.generateAccountStatement(clientId, startDate, endDate);
    }
}