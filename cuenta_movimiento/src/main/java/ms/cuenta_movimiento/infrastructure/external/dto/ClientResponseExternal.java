package ms.cuenta_movimiento.infrastructure.external.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseExternal {
    private Long id;
    private String name;
    private String identification;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
    private Boolean status;
    private String password;
}