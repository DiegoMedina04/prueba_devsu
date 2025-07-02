package ms.cuenta_movimiento.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientReport {
    private String clientName;
    private Long clientId;
    private List<AccountStatement> accountStatements;
}