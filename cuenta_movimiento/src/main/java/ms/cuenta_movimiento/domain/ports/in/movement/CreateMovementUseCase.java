package ms.cuenta_movimiento.domain.ports.in.movement;

import ms.cuenta_movimiento.domain.models.Movement;

public interface CreateMovementUseCase {
    Movement save(Movement movement);
}