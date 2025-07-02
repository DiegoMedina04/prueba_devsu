package ms.cliente_persona.infrastructure.repositories.client;

import ms.cliente_persona.domain.models.Client;
import ms.cliente_persona.domain.ports.out.client.ClientRepositoryPort;
import ms.cliente_persona.infrastructure.entities.ClientEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaClientRepositoryAdapter implements ClientRepositoryPort {

    private final JpaClientRepository jpaRepository;

    public JpaClientRepositoryAdapter(JpaClientRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Client> getClients() {
        return jpaRepository.findAll()
                .stream()
                .map(ClientEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return jpaRepository.findById(id)
                .map(ClientEntity::toDomainModel);
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = ClientEntity.fromDomainModel(client);
        ClientEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Client> update(Client client) {
        Optional<ClientEntity> existingEntityOpt = jpaRepository.findById(client.getId());
        if (existingEntityOpt.isEmpty()) {
            return Optional.empty();
        }
        
        ClientEntity existingEntity = existingEntityOpt.get();
        ClientEntity updatedEntity = ClientEntity.fromDomainModelForUpdate(client, existingEntity);
        ClientEntity savedEntity = jpaRepository.save(updatedEntity);
        return Optional.of(savedEntity.toDomainModel());
    }

    @Override
    public Boolean delete(Long id) {
        if (!jpaRepository.existsById(id)) {
            return false;
        }
        
        jpaRepository.deleteById(id);
        return true;
    }
}
