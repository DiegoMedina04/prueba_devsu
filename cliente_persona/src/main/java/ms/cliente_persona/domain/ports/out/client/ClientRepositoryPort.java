package ms.cliente_persona.domain.ports.out.client;

import ms.cliente_persona.domain.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPort {
    List<Client> getClients();
    Optional<Client> getClientById(Long id);
    Client save(Client client);
    Optional<Client> update(Client client);
    Boolean delete(Long id);
}
