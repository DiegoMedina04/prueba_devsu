package ms.cuenta_movimiento.application.useCases.movement;

import ms.cuenta_movimiento.domain.exceptions.AccountNotFoundException;
import ms.cuenta_movimiento.domain.exceptions.InsufficientFundsException;
import ms.cuenta_movimiento.domain.models.Account;
import ms.cuenta_movimiento.domain.models.Movement;
import ms.cuenta_movimiento.domain.ports.in.movement.CreateMovementUseCase;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;
import ms.cuenta_movimiento.domain.ports.out.movement.MovementRepositoryPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CreateMovementUseCaseImpl implements CreateMovementUseCase {

    private final MovementRepositoryPort movementRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;

    public CreateMovementUseCaseImpl(MovementRepositoryPort movementRepositoryPort, 
                                   AccountRepositoryPort accountRepositoryPort) {
        this.movementRepositoryPort = movementRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public Movement save(Movement movement) {
        Optional<Account> accountOpt = accountRepositoryPort.getAccountById(movement.getAccountId());
        if (accountOpt.isEmpty()) {
            throw new AccountNotFoundException("Cuenta no encontrada con ID: " + movement.getAccountId());
        }

        Account account = accountOpt.get();
        Double currentBalance = getCurrentBalance(account);
        Double newBalance = currentBalance + movement.getValue();
        
        if (movement.getValue() < 0 && newBalance < 0) {
            throw new InsufficientFundsException("Saldo no disponible");
        }
        
        if (movement.getDate() == null) {
            movement.setDate(LocalDate.now());
        }
        
        movement.setBalance(newBalance);
        
        if (movement.getMovementType() == null || movement.getMovementType().isEmpty()) {
            movement.setMovementType(movement.getValue() > 0 ? "Deposito" : "Retiro");
        }
        
        return movementRepositoryPort.save(movement);
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