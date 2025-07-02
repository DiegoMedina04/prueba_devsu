package ms.cuenta_movimiento.infrastructure.external.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfo {
    private Long clientId;
    private String name;
    private String identification;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
    private Boolean status;
    private LocalDateTime lastUpdated;
    
    public ClientInfo(Long clientId, String name, String identification, Boolean status) {
        this.clientId = clientId;
        this.name = name;
        this.identification = identification;
        this.status = status;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public ClientInfo(String name) {
        this.name = name;
        this.lastUpdated = LocalDateTime.now();
    }
}