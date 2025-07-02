package ms.cliente_persona.application.useCases.client;

import ms.cliente_persona.domain.exceptions.BadRequestException;
import ms.cliente_persona.domain.models.Client;
import ms.cliente_persona.domain.ports.in.client.CreateClientUseCase;
import ms.cliente_persona.domain.ports.out.client.ClientRepositoryPort;
import ms.cliente_persona.infrastructure.events.ClientEventPublisher;
import ms.cliente_persona.infrastructure.events.dto.ClientCreatedEvent;

public class CreateClientUseCaseImpl implements CreateClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientEventPublisher eventPublisher;

    public CreateClientUseCaseImpl(ClientRepositoryPort clientRepositoryPort, ClientEventPublisher eventPublisher) {
        this.clientRepositoryPort = clientRepositoryPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Client save(Client client) {
        Client clientCreated = clientRepositoryPort.save(client);
        
        ClientCreatedEvent event = new ClientCreatedEvent(
                clientCreated.getId(),
                clientCreated.getPersona().getName(),
                clientCreated.getPersona().getIdentification(),
                clientCreated.getPersona().getGender(),
                clientCreated.getPersona().getAge(),
                clientCreated.getPersona().getAddress(),
                clientCreated.getPersona().getTelephone(),
                clientCreated.getStatus()
        );
        
        eventPublisher.publishClientCreated(event);
        
        return clientCreated;
    }
}
