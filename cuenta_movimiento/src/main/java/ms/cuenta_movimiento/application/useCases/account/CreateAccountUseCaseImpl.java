package ms.cuenta_movimiento.application.useCases.account;

import ms.cuenta_movimiento.domain.exceptions.ClientNotFoundException;
import ms.cuenta_movimiento.domain.models.Account;
import ms.cuenta_movimiento.domain.ports.in.account.CreateAccountUseCase;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;
import ms.cuenta_movimiento.domain.ports.out.external.ClientServicePort;

public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final ClientServicePort clientServicePort;

    public CreateAccountUseCaseImpl(AccountRepositoryPort accountRepositoryPort, ClientServicePort clientServicePort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.clientServicePort = clientServicePort;
    }

    @Override
    public Account save(Account account) {
        boolean clientExists = clientServicePort.existsById(account.getClientId());

        if (!clientExists) {
            throw new ClientNotFoundException("Cliente no encontrado");
        }
        
        return accountRepositoryPort.save(account);
    }
}