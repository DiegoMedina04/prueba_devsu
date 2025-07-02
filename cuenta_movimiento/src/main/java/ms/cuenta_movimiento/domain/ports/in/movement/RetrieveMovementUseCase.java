package ms.cuenta_movimiento.domain.ports.in.movement;

import ms.cuenta_movimiento.domain.models.Movement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RetrieveMovementUseCase {
    List<Movement> getMovements();
    Optional<Movement> getMovementById(Long id);
    List<Movement> getMovementsByAccountId(Long accountId);
    List<Movement> getMovementsByAccountIdAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate);
    List<Movement> getMovementsByClientIdAndDateRange(Long clientId, LocalDate startDate, LocalDate endDate);
}