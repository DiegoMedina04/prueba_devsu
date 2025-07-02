package ms.cuenta_movimiento.domain.ports.in.movement;

import ms.cuenta_movimiento.domain.models.Movement;

import java.util.Optional;

public interface UpdateMovementUseCase {
    Optional<Movement> update(Movement movement);
}