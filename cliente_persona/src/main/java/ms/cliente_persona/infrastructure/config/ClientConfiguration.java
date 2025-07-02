package ms.cliente_persona.infrastructure.config;

import ms.cliente_persona.application.services.ClientService;
import ms.cliente_persona.application.useCases.client.CreateClientUseCaseImpl;
import ms.cliente_persona.application.useCases.client.DeleteClienteUseCaseImpl;
import ms.cliente_persona.application.useCases.client.RetrieveClientUseCaseImpl;
import ms.cliente_persona.application.useCases.client.UpdateClientUseCaseImpl;
import ms.cliente_persona.domain.ports.in.client.CreateClientUseCase;
import ms.cliente_persona.domain.ports.in.client.DeleteClienteUseCase;
import ms.cliente_persona.domain.ports.in.client.RetrieveClientUseCase;
import ms.cliente_persona.domain.ports.in.client.UpdateClientUseCase;
import ms.cliente_persona.domain.ports.out.client.ClientRepositoryPort;
import ms.cliente_persona.infrastructure.events.ClientEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public RetrieveClientUseCase retrieveClientUseCase(ClientRepositoryPort port) {
        return new RetrieveClientUseCaseImpl(port);
    }

    @Bean
    public CreateClientUseCase createClientUseCase(ClientRepositoryPort port, ClientEventPublisher eventPublisher) {
        return new CreateClientUseCaseImpl(port, eventPublisher);
    }

    @Bean
    public UpdateClientUseCase updateClientUseCase(ClientRepositoryPort port) {
        return new UpdateClientUseCaseImpl(port);
    }

    @Bean
    public DeleteClienteUseCase deleteClientUseCase(ClientRepositoryPort port, ClientEventPublisher eventPublisher) {
        return new DeleteClienteUseCaseImpl(port, eventPublisher);
    }

    @Bean
    public ClientService clientService(
            RetrieveClientUseCase retrieveClientUseCase,
            CreateClientUseCase createClientUseCase,
            UpdateClientUseCase updateClientUseCase,
            DeleteClienteUseCase deleteClientUseCase
    ) {
        return new ClientService(
                createClientUseCase,
                retrieveClientUseCase,
                updateClientUseCase,
                deleteClientUseCase
        );
    }
}
