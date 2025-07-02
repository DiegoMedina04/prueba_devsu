package ms.cuenta_movimiento.infrastructure.config;
import ms.cuenta_movimiento.application.services.AccountService;
import ms.cuenta_movimiento.application.useCases.account.CreateAccountUseCaseImpl;
import ms.cuenta_movimiento.application.useCases.account.RetrieveAccountUseCaseImpl;
import ms.cuenta_movimiento.application.useCases.account.UpdateAccountUseCaseImpl;
import ms.cuenta_movimiento.domain.ports.in.account.CreateAccountUseCase;
import ms.cuenta_movimiento.domain.ports.in.account.RetrieveAccountUseCase;
import ms.cuenta_movimiento.domain.ports.in.account.UpdateAccountUseCase;
import ms.cuenta_movimiento.domain.ports.out.account.AccountRepositoryPort;
import ms.cuenta_movimiento.domain.ports.out.external.ClientServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    @Bean
    public CreateAccountUseCase createAccountUseCase(
            AccountRepositoryPort accountRepositoryPort,
            ClientServicePort clientServicePort) {
        return new CreateAccountUseCaseImpl(accountRepositoryPort, clientServicePort);
    }

    @Bean
    public RetrieveAccountUseCase retrieveAccountUseCase(AccountRepositoryPort accountRepositoryPort) {
        return new RetrieveAccountUseCaseImpl(accountRepositoryPort);
    }

    @Bean
    public UpdateAccountUseCase updateAccountUseCase(AccountRepositoryPort accountRepositoryPort) {
        return new UpdateAccountUseCaseImpl(accountRepositoryPort);
    }

    @Bean
    public AccountService accountService(
            CreateAccountUseCase createAccountUseCase,
            RetrieveAccountUseCase retrieveAccountUseCase,
            UpdateAccountUseCase updateAccountUseCase) {
        return new AccountService(createAccountUseCase, retrieveAccountUseCase, updateAccountUseCase);
    }
}