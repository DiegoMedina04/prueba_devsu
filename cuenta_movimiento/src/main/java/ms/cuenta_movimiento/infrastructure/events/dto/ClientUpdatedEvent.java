package ms.cuenta_movimiento.infrastructure.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdatedEvent {
    private Long clientId;
    private String name;
    private String identification;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
    private Boolean status;
    private LocalDateTime timestamp;
}