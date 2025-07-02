package ms.cuenta_movimiento.domain.ports.in.account;

import ms.cuenta_movimiento.domain.models.Account;

import java.util.List;
import java.util.Optional;

public interface RetrieveAccountUseCase {
    List<Account> getAccounts();
    Optional<Account> getAccountById(Long id);
    Optional<Account> getAccountByNumber(String accountNumber);
    List<Account> getAccountsByClientId(Long clientId);
}