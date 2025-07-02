package ms.cuenta_movimiento.application.useCases.account;

import ms.cuenta_movimiento.domain.models.Account;
import ms.cuenta_movimiento.domain.ports.in.account.UpdateAccountUseCase;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;

import java.util.Optional;

public class UpdateAccountUseCaseImpl implements UpdateAccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public UpdateAccountUseCaseImpl(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public Optional<Account> update(Account account) {
        return accountRepositoryPort.update(account);
    }
}