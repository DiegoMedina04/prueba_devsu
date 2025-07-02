package ms.cuenta_movimiento.application.services;

import ms.cuenta_movimiento.domain.models.Account;
import ms.cuenta_movimiento.domain.ports.in.account.CreateAccountUseCase;
import ms.cuenta_movimiento.domain.ports.in.account.RetrieveAccountUseCase;
import ms.cuenta_movimiento.domain.ports.in.account.UpdateAccountUseCase;
import java.util.List;
import java.util.Optional;

public class AccountService {

    private final CreateAccountUseCase createAccountUseCase;
    private final RetrieveAccountUseCase retrieveAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;

    public AccountService(CreateAccountUseCase createAccountUseCase,
                         RetrieveAccountUseCase retrieveAccountUseCase,
                         UpdateAccountUseCase updateAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.retrieveAccountUseCase = retrieveAccountUseCase;
        this.updateAccountUseCase = updateAccountUseCase;
    }

    public Account createAccount(Account account) {
        return createAccountUseCase.save(account);
    }

    public List<Account> getAllAccounts() {
        return retrieveAccountUseCase.getAccounts();
    }

    public Optional<Account> getAccountById(Long id) {
        return retrieveAccountUseCase.getAccountById(id);
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return retrieveAccountUseCase.getAccountByNumber(accountNumber);
    }

    public List<Account> getAccountsByClientId(Long clientId) {
        return retrieveAccountUseCase.getAccountsByClientId(clientId);
    }

    public Optional<Account> updateAccount(Account account) {
        return updateAccountUseCase.update(account);
    }
}