package ms.cliente_persona.infrastructure.events.dto;

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
    
    public ClientUpdatedEvent(Long clientId, String name, String identification, String gender, 
                            Integer age, String address, String phone, Boolean status) {
        this.clientId = clientId;
        this.name = name;
        this.identification = identification;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}