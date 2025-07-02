package ms.cuenta_movimiento.application.useCases.movement;

import ms.cuenta_movimiento.domain.models.Movement;
import ms.cuenta_movimiento.domain.ports.in.movement.RetrieveMovementUseCase;
import ms.cuenta_movimiento.domain.ports.out.movement.MovementRepositoryPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RetrieveMovementUseCaseImpl implements RetrieveMovementUseCase {

    private final MovementRepositoryPort movementRepositoryPort;

    public RetrieveMovementUseCaseImpl(MovementRepositoryPort movementRepositoryPort) {
        this.movementRepositoryPort = movementRepositoryPort;
    }

    @Override
    public List<Movement> getMovements() {
        return movementRepositoryPort.getMovements();
    }

    @Override
    public Optional<Movement> getMovementById(Long id) {
        return movementRepositoryPort.getMovementById(id);
    }

    @Override
    public List<Movement> getMovementsByAccountId(Long accountId) {
        return movementRepositoryPort.getMovementsByAccountId(accountId);
    }

    @Override
    public List<Movement> getMovementsByAccountIdAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate) {
        return movementRepositoryPort.getMovementsByAccountIdAndDateRange(accountId, startDate, endDate);
    }

    @Override
    public List<Movement> getMovementsByClientIdAndDateRange(Long clientId, LocalDate startDate, LocalDate endDate) {
        return movementRepositoryPort.getMovementsByClientIdAndDateRange(clientId, startDate, endDate);
    }
}