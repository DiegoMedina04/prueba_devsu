package ms.cliente_persona.application.useCases.client;

import ms.cliente_persona.domain.models.Client;
import ms.cliente_persona.domain.ports.in.client.UpdateClientUseCase;
import ms.cliente_persona.domain.ports.out.client.ClientRepositoryPort;

import java.util.Optional;

public class UpdateClientUseCaseImpl implements UpdateClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    public UpdateClientUseCaseImpl(ClientRepositoryPort clientRepositoryPort) {
        this.clientRepositoryPort = clientRepositoryPort;
    }

    @Override
    public Optional<Client> updateClient(Client client) {
        return clientRepositoryPort.update(client);
    }
}