package ms.cuenta_movimiento.application.services;

import ms.cuenta_movimiento.domain.models.Movement;
import ms.cuenta_movimiento.domain.ports.in.movement.CreateMovementUseCase;
import ms.cuenta_movimiento.domain.ports.in.movement.RetrieveMovementUseCase;
import ms.cuenta_movimiento.domain.ports.in.movement.UpdateMovementUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    private final CreateMovementUseCase createMovementUseCase;
    private final RetrieveMovementUseCase retrieveMovementUseCase;
    private final UpdateMovementUseCase updateMovementUseCase;

    public MovementService(CreateMovementUseCase createMovementUseCase,
                          RetrieveMovementUseCase retrieveMovementUseCase,
                          UpdateMovementUseCase updateMovementUseCase) {
        this.createMovementUseCase = createMovementUseCase;
        this.retrieveMovementUseCase = retrieveMovementUseCase;
        this.updateMovementUseCase = updateMovementUseCase;
    }

    public Movement createMovement(Movement movement) {
        return createMovementUseCase.save(movement);
    }

    public List<Movement> getAllMovements() {
        return retrieveMovementUseCase.getMovements();
    }

    public Optional<Movement> getMovementById(Long id) {
        return retrieveMovementUseCase.getMovementById(id);
    }

    public List<Movement> getMovementsByAccountId(Long accountId) {
        return retrieveMovementUseCase.getMovementsByAccountId(accountId);
    }

    public List<Movement> getMovementsByAccountIdAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate) {
        return retrieveMovementUseCase.getMovementsByAccountIdAndDateRange(accountId, startDate, endDate);
    }

    public List<Movement> getMovementsByClientIdAndDateRange(Long clientId, LocalDate startDate, LocalDate endDate) {
        return retrieveMovementUseCase.getMovementsByClientIdAndDateRange(clientId, startDate, endDate);
    }

    public Optional<Movement> updateMovement(Movement movement) {
        return updateMovementUseCase.update(movement);
    }
}