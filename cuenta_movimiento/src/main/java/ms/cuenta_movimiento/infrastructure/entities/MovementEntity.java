package ms.cuenta_movimiento.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.cuenta_movimiento.domain.models.Movement;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimiento")
public class MovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate date;
    
    @Column(name = "tipo_movimiento", nullable = false)
    private String movementType;
    
    @Column(name = "valor", nullable = false)
    private Double value;
    
    @Column(name = "saldo", nullable = false)
    private Double balance;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private AccountEntity account;

    public static MovementEntity fromDomainModel(Movement movement, AccountEntity accountEntity) {
        return new MovementEntity(
                movement.getId(),
                movement.getDate(),
                movement.getMovementType(),
                movement.getValue(),
                movement.getBalance(),
                accountEntity
        );
    }
    
    public static MovementEntity fromDomainModelForUpdate(Movement movement, MovementEntity existingEntity, AccountEntity accountEntity) {
        existingEntity.setDate(movement.getDate());
        existingEntity.setMovementType(movement.getMovementType());
        existingEntity.setValue(movement.getValue());
        existingEntity.setBalance(movement.getBalance());
        existingEntity.setAccount(accountEntity);
        
        return existingEntity;
    }

    public Movement toDomainModel() {
        return new Movement(
            this.id,
            this.date,
            this.movementType,
            this.value,
            this.balance,
            this.account != null ? this.account.getId() : null
        );
    }
}