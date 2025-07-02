package ms.cuenta_movimiento.application.useCases.movement;

import ms.cuenta_movimiento.domain.models.Movement;
import ms.cuenta_movimiento.domain.ports.in.movement.UpdateMovementUseCase;
import ms.cuenta_movimiento.domain.ports.out.movement.MovementRepositoryPort;

import java.util.Optional;

public class UpdateMovementUseCaseImpl implements UpdateMovementUseCase {

    private final MovementRepositoryPort movementRepositoryPort;

    public UpdateMovementUseCaseImpl(MovementRepositoryPort movementRepositoryPort) {
        this.movementRepositoryPort = movementRepositoryPort;
    }

    @Override
    public Optional<Movement> update(Movement movement) {
        return movementRepositoryPort.update(movement);
    }
}