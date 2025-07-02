package ms.cuenta_movimiento.application.useCases.report;

import ms.cuenta_movimiento.domain.models.Account;
import ms.cuenta_movimiento.domain.models.AccountStatement;
import ms.cuenta_movimiento.domain.models.Movement;
import ms.cuenta_movimiento.domain.ports.in.report.GenerateReportUseCase;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;
import ms.cuenta_movimiento.domain.ports.out.external.ClientServicePort;
import ms.cuenta_movimiento.domain.ports.out.movement.MovementRepositoryPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenerateReportUseCaseImpl implements GenerateReportUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final MovementRepositoryPort movementRepositoryPort;
    private final ClientServicePort clientServicePort;

    public GenerateReportUseCaseImpl(AccountRepositoryPort accountRepositoryPort,
                                   MovementRepositoryPort movementRepositoryPort,
                                   ClientServicePort clientServicePort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.movementRepositoryPort = movementRepositoryPort;
        this.clientServicePort = clientServicePort;
    }

    @Override
    public List<AccountStatement> generateAccountStatement(Long clientId, LocalDate startDate, LocalDate endDate) {
        List<AccountStatement> statements = new ArrayList<>();
        
        String clientName = clientServicePort.getClientNameById(clientId)
                .orElse("Cliente no encontrado");
        
        List<Account> accounts = accountRepositoryPort.getAccountsByClientId(clientId);
        
        for (Account account : accounts) {
            List<Movement> movements = movementRepositoryPort.getMovementsByAccountIdAndDateRange(
                    account.getId(), startDate, endDate);
            
            if (movements.isEmpty()) {
                AccountStatement statement = new AccountStatement();
                statement.setFecha(null);
                statement.setCliente(clientName);
                statement.setNumeroCuenta(account.getAccountNumber());
                statement.setTipo(account.getAccountType());
                statement.setSaldoInicial(account.getInitialBalance());
                statement.setEstado(account.getStatus());
                statement.setMovimiento(0.0);
                statement.setSaldoDisponible(getCurrentBalance(account));
                
                statements.add(statement);
            } else {
                for (Movement movement : movements) {
                    AccountStatement statement = new AccountStatement();
                    statement.setFecha(movement.getDate());
                    statement.setCliente(clientName);
                    statement.setNumeroCuenta(account.getAccountNumber());
                    statement.setTipo(account.getAccountType());
                    statement.setSaldoInicial(account.getInitialBalance());
                    statement.setEstado(account.getStatus());
                    statement.setMovimiento(movement.getValue());
                    statement.setSaldoDisponible(movement.getBalance());
                    
                    statements.add(statement);
                }
            }
        }
        
        return statements;
    }
    
    private Double getCurrentBalance(Account account) {
        List<Movement> movements = movementRepositoryPort.getMovementsByAccountId(account.getId());
        
        if (movements.isEmpty()) {
            return account.getInitialBalance();
        } else {
            return movements.stream()
                    .max((m1, m2) -> m1.getDate().compareTo(m2.getDate()))
                    .map(Movement::getBalance)
                    .orElse(account.getInitialBalance());
        }
    }
}