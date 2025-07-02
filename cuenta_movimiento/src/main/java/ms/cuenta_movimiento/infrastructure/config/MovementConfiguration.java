package ms.cuenta_movimiento.infrastructure.config;

import ms.cuenta_movimiento.application.services.MovementService;
import ms.cuenta_movimiento.application.useCases.movement.CreateMovementUseCaseImpl;
import ms.cuenta_movimiento.application.useCases.movement.RetrieveMovementUseCaseImpl;
import ms.cuenta_movimiento.application.useCases.movement.UpdateMovementUseCaseImpl;
import ms.cuenta_movimiento.domain.ports.in.movement.CreateMovementUseCase;
import ms.cuenta_movimiento.domain.ports.in.movement.RetrieveMovementUseCase;
import ms.cuenta_movimiento.domain.ports.in.movement.UpdateMovementUseCase;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;
import ms.cuenta_movimiento.domain.ports.out.movement.MovementRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovementConfiguration {

    @Bean
    public CreateMovementUseCase createMovementUseCase(
            MovementRepositoryPort movementRepositoryPort,
            AccountRepositoryPort accountRepositoryPort) {
        return new CreateMovementUseCaseImpl(movementRepositoryPort, accountRepositoryPort);
    }

    @Bean
    public RetrieveMovementUseCase retrieveMovementUseCase(MovementRepositoryPort movementRepositoryPort) {
        return new RetrieveMovementUseCaseImpl(movementRepositoryPort);
    }

    @Bean
    public UpdateMovementUseCase updateMovementUseCase(MovementRepositoryPort movementRepositoryPort) {
        return new UpdateMovementUseCaseImpl(movementRepositoryPort);
    }

    @Bean
    public MovementService movementService(
            CreateMovementUseCase createMovementUseCase,
            RetrieveMovementUseCase retrieveMovementUseCase,
            UpdateMovementUseCase updateMovementUseCase
    ) {
        return new MovementService(
                createMovementUseCase,
                retrieveMovementUseCase,
                updateMovementUseCase
        );
    }
}