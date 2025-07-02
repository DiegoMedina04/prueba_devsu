package ms.cuenta_movimiento.domain.ports.in.account;

import ms.cuenta_movimiento.domain.models.Account;

import java.util.Optional;

public interface UpdateAccountUseCase {
    Optional<Account> update(Account account);
}