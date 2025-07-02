package ms.cuenta_movimiento.infrastructure.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDeletedEvent {
    private Long clientId;
    private String identification;
    private LocalDateTime timestamp;
}