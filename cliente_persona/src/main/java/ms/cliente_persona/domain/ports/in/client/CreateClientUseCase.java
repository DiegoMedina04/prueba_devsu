package ms.cliente_persona.domain.ports.in.client;

import ms.cliente_persona.domain.models.Client;

public interface CreateClientUseCase {
    Client save(Client client);
}
