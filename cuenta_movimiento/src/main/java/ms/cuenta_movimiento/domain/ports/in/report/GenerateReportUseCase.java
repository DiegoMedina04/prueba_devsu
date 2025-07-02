package ms.cuenta_movimiento.domain.ports.in.report;

import ms.cuenta_movimiento.domain.models.AccountStatement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GenerateReportUseCase {
    List<AccountStatement> generateAccountStatement(Long clientId, LocalDate startDate, LocalDate endDate);
}