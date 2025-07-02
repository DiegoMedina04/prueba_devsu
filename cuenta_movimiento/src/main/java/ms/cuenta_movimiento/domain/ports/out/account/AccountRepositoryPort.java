package ms.cuenta_movimiento.domain.ports.out.account;

import ms.cuenta_movimiento.domain.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {
    List<Account> getAccounts();
    Optional<Account> getAccountById(Long id);
    Optional<Account> getAccountByNumber(String accountNumber);
    List<Account> getAccountsByClientId(Long clientId);
    Account save(Account account);
    Optional<Account> update(Account account);
    Boolean delete(Long id);
}