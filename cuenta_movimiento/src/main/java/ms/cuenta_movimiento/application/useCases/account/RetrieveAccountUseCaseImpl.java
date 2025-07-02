package ms.cuenta_movimiento.application.useCases.account;

import ms.cuenta_movimiento.domain.models.Account;
import ms.cuenta_movimiento.domain.ports.in.account.RetrieveAccountUseCase;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;

import java.util.List;
import java.util.Optional;

public class RetrieveAccountUseCaseImpl implements RetrieveAccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public RetrieveAccountUseCaseImpl(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepositoryPort.getAccounts();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepositoryPort.getAccountById(id);
    }

    @Override
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepositoryPort.getAccountByNumber(accountNumber);
    }

    @Override
    public List<Account> getAccountsByClientId(Long clientId) {
        return accountRepositoryPort.getAccountsByClientId(clientId);
    }
}