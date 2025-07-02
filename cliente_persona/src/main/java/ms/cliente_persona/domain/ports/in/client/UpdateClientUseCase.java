package ms.cliente_persona.domain.ports.in.client;

import ms.cliente_persona.domain.models.Client;

import java.util.Optional;

public interface UpdateClientUseCase {
    Optional<Client> updateClient(Client client);
}
