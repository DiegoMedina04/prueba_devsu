package ms.cuenta_movimiento.infrastructure.repositories.movement;

import ms.cuenta_movimiento.domain.models.Movement;
import ms.cuenta_movimiento.domain.ports.out.movement.MovementRepositoryPort;
import ms.cuenta_movimiento.infrastructure.entities.AccountEntity;
import ms.cuenta_movimiento.infrastructure.entities.MovementEntity;
import ms.cuenta_movimiento.infrastructure.repositories.account.JpaAccountRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaMovementRepositoryAdapter implements MovementRepositoryPort {

    private final JpaMovementRepository jpaMovementRepository;
    private final JpaAccountRepository jpaAccountRepository;

    public JpaMovementRepositoryAdapter(JpaMovementRepository jpaMovementRepository, 
                                       JpaAccountRepository jpaAccountRepository) {
        this.jpaMovementRepository = jpaMovementRepository;
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public List<Movement> getMovements() {
        return jpaMovementRepository.findAll()
                .stream()
                .map(MovementEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movement> getMovementById(Long id) {
        return jpaMovementRepository.findById(id)
                .map(MovementEntity::toDomainModel);
    }

    @Override
    public List<Movement> getMovementsByAccountId(Long accountId) {
        return jpaMovementRepository.findByAccountId(accountId)
                .stream()
                .map(MovementEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movement> getMovementsByAccountIdAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate) {
        return jpaMovementRepository.findByAccountIdAndDateRange(accountId, startDate, endDate)
                .stream()
                .map(MovementEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movement> getMovementsByClientIdAndDateRange(Long clientId, LocalDate startDate, LocalDate endDate) {
        return jpaMovementRepository.findByClientIdAndDateRange(clientId, startDate, endDate)
                .stream()
                .map(MovementEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Movement save(Movement movement) {
        Optional<AccountEntity> accountEntityOpt = jpaAccountRepository.findById(movement.getAccountId());
        if (accountEntityOpt.isEmpty()) {
            throw new RuntimeException("Account not found with id: " + movement.getAccountId());
        }
        
        MovementEntity entity = MovementEntity.fromDomainModel(movement, accountEntityOpt.get());
        MovementEntity savedEntity = jpaMovementRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Movement> update(Movement movement) {
        Optional<MovementEntity> existingEntityOpt = jpaMovementRepository.findById(movement.getId());
        if (existingEntityOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Optional<AccountEntity> accountEntityOpt = jpaAccountRepository.findById(movement.getAccountId());
        if (accountEntityOpt.isEmpty()) {
            throw new RuntimeException("Account not found with id: " + movement.getAccountId());
        }
        
        MovementEntity existingEntity = existingEntityOpt.get();
        MovementEntity updatedEntity = MovementEntity.fromDomainModelForUpdate(movement, existingEntity, accountEntityOpt.get());
        MovementEntity savedEntity = jpaMovementRepository.save(updatedEntity);
        return Optional.of(savedEntity.toDomainModel());
    }

    @Override
    public Boolean delete(Long id) {
        if (!jpaMovementRepository.existsById(id)) {
            return false;
        }
        
        jpaMovementRepository.deleteById(id);
        return true;
    }
}