package ms.cuenta_movimiento.domain.ports.out.external;

import java.util.Optional;

public interface ClientServicePort {
    Optional<String> getClientNameById(Long clientId);
    boolean existsById(Long clientId);
}