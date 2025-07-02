package ms.cliente_persona.domain.ports.in.client;

import ms.cliente_persona.domain.models.Client;

import java.util.List;
import java.util.Optional;

public interface RetrieveClientUseCase {
    List<Client> retrieveClients();
    Optional<Client> getClientById(Long id);
}
