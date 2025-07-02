package ms.cliente_persona.application.services;

import ms.cliente_persona.domain.models.Client;
import ms.cliente_persona.domain.ports.in.client.CreateClientUseCase;
import ms.cliente_persona.domain.ports.in.client.DeleteClienteUseCase;
import ms.cliente_persona.domain.ports.in.client.RetrieveClientUseCase;
import ms.cliente_persona.domain.ports.in.client.UpdateClientUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final CreateClientUseCase createClientUseCase;
    private final RetrieveClientUseCase retrieveClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClienteUseCase deleteClienteUseCase;

    public ClientService(CreateClientUseCase createClientUseCase,
                        RetrieveClientUseCase retrieveClientUseCase,
                        UpdateClientUseCase updateClientUseCase,
                        DeleteClienteUseCase deleteClienteUseCase) {
        this.createClientUseCase = createClientUseCase;
        this.retrieveClientUseCase = retrieveClientUseCase;
        this.updateClientUseCase = updateClientUseCase;
        this.deleteClienteUseCase = deleteClienteUseCase;
    }

    public Client createClient(Client client) {
        return createClientUseCase.save(client);
    }

    public List<Client> getAllClients() {
        return retrieveClientUseCase.retrieveClients();
    }

    public Optional<Client> getClientById(Long id) {
        return retrieveClientUseCase.getClientById(id);
    }

    public Optional<Client> updateClient(Client client) {
        return updateClientUseCase.updateClient(client);
    }

    public Boolean deleteClient(Long id) {
        return deleteClienteUseCase.delete(id);
    }
}
