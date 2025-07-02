package ms.cliente_persona.domain.ports.in.client;

import ms.cliente_persona.domain.models.Client;

public interface DeleteClienteUseCase {
    Boolean delete(Long clienteId);
}
