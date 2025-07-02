package ms.cuenta_movimiento.infrastructure.repositories.account;

import ms.cuenta_movimiento.domain.models.Account;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;
import ms.cuenta_movimiento.infrastructure.entities.AccountEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {

    private final JpaAccountRepository jpaRepository;

    public JpaAccountRepositoryAdapter(JpaAccountRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Account> getAccounts() {
        return jpaRepository.findAll()
                .stream()
                .map(AccountEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return jpaRepository.findById(id)
                .map(AccountEntity::toDomainModel);
    }

    @Override
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return jpaRepository.findByAccountNumber(accountNumber)
                .map(AccountEntity::toDomainModel);
    }

    @Override
    public List<Account> getAccountsByClientId(Long clientId) {
        return jpaRepository.findByClientId(clientId)
                .stream()
                .map(AccountEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = AccountEntity.fromDomainModel(account);
        AccountEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomainModel();
    }

    @Override
    public Optional<Account> update(Account account) {
        Optional<AccountEntity> existingEntityOpt = jpaRepository.findById(account.getId());
        if (existingEntityOpt.isEmpty()) {
            return Optional.empty();
        }
        
        AccountEntity existingEntity = existingEntityOpt.get();
        AccountEntity updatedEntity = AccountEntity.fromDomainModelForUpdate(account, existingEntity);
        AccountEntity savedEntity = jpaRepository.save(updatedEntity);
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