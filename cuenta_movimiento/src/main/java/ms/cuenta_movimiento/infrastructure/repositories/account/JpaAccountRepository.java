package ms.cuenta_movimiento.infrastructure.repositories.account;

import ms.cuenta_movimiento.infrastructure.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaAccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
    List<AccountEntity> findByClientId(Long clientId);
}