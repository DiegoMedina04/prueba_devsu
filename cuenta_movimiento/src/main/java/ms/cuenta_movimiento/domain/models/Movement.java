package ms.cuenta_movimiento.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movement {
    private Long id;
    private LocalDate date;
    private String movementType;
    private Double value;
    private Double balance;
    private Long accountId;
}
