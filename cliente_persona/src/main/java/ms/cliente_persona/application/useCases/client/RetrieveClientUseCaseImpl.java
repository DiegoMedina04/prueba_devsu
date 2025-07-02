package ms.cliente_persona.application.useCases.client;

import ms.cliente_persona.domain.exceptions.NotFoundException;
import ms.cliente_persona.domain.models.Client;
import ms.cliente_persona.domain.ports.in.client.RetrieveClientUseCase;
import ms.cliente_persona.domain.ports.out.client.ClientRepositoryPort;

import java.util.List;
import java.util.Optional;

public class RetrieveClientUseCaseImpl implements RetrieveClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    public RetrieveClientUseCaseImpl(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    public List<Client> retrieveClients() {
        return clientRepositoryPort.getClients();
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        Optional<Client> client = clientRepositoryPort.getClientById(id);
        if (client.isEmpty()) {
            throw new NotFoundException("Cliente con ID " + id + " no encontrado");
        }
        return client;
    }
}