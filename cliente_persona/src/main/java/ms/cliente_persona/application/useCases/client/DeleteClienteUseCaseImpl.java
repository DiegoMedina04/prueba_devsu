package ms.cliente_persona.application.useCases.client;

import ms.cliente_persona.domain.models.Client;
import ms.cliente_persona.domain.ports.in.client.DeleteClienteUseCase;
import ms.cliente_persona.domain.ports.out.client.ClientRepositoryPort;
import ms.cliente_persona.infrastructure.events.ClientEventPublisher;
import ms.cliente_persona.infrastructure.events.dto.ClientDeletedEvent;

public class DeleteClienteUseCaseImpl implements DeleteClienteUseCase {

    private final ClientRepositoryPort clientRepositoryPort;
    private final ClientEventPublisher eventPublisher;

    public DeleteClienteUseCaseImpl(ClientRepositoryPort clientRepositoryPort, ClientEventPublisher eventPublisher) {
        this.clientRepositoryPort = clientRepositoryPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Boolean delete(Long clienteId) {
        Client client = clientRepositoryPort.getClientById(clienteId).orElse(null);
        
        Boolean deleted = clientRepositoryPort.delete(clienteId);
        
        if (deleted && client != null) {
            ClientDeletedEvent event = new ClientDeletedEvent(
                    client.getId(),
                    client.getPersona().getIdentification()
            );
            eventPublisher.publishClientDeleted(event);
        }
        
        return deleted;
    }
}