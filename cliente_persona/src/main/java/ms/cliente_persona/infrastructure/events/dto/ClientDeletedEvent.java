package ms.cliente_persona.infrastructure.events.dto;

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
    
    public ClientDeletedEvent(Long clientId, String identification) {
        this.clientId = clientId;
        this.identification = identification;
        this.timestamp = LocalDateTime.now();
    }
}