package ms.cuenta_movimiento.domain.ports.in.account;

import ms.cuenta_movimiento.domain.models.Account;

public interface CreateAccountUseCase {
    Account save(Account account);
}