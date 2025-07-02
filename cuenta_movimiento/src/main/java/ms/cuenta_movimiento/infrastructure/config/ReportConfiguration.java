package ms.cuenta_movimiento.infrastructure.config;

import ms.cuenta_movimiento.application.services.ReportService;
import ms.cuenta_movimiento.application.useCases.report.GenerateReportUseCaseImpl;
import ms.cuenta_movimiento.domain.ports.in.report.GenerateReportUseCase;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;
import ms.cuenta_movimiento.domain.ports.out.external.ClientServicePort;
import ms.cuenta_movimiento.domain.ports.out.movement.MovementRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfiguration {

    @Bean
    public GenerateReportUseCase generateReportUseCase(
            AccountRepositoryPort accountRepositoryPort,
            MovementRepositoryPort movementRepositoryPort,
            ClientServicePort clientServicePort) {
        return new GenerateReportUseCaseImpl(accountRepositoryPort, movementRepositoryPort, clientServicePort);
    }

    @Bean
    public ReportService reportService(GenerateReportUseCase generateReportUseCase) {
        return new ReportService(generateReportUseCase);
    }
}