package ms.cuenta_movimiento.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.cuenta_movimiento.domain.models.Account;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuenta")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String accountNumber;
    
    @Column(name = "tipo_cuenta", nullable = false)
    private String accountType;
    
    @Column(name = "saldo_inicial", nullable = false)
    private Double initialBalance;
    
    @Column(name = "estado", nullable = false)
    private Boolean status;
    
    @Column(name = "cliente_id", nullable = false)
    private Long clientId;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovementEntity> movements;

    public static AccountEntity fromDomainModel(Account account) {
        return new AccountEntity(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getStatus(),
                account.getClientId(),
                null
        );
    }
    
    public static AccountEntity fromDomainModelForUpdate(Account account, AccountEntity existingEntity) {
        existingEntity.setAccountNumber(account.getAccountNumber());
        existingEntity.setAccountType(account.getAccountType());
        existingEntity.setInitialBalance(account.getInitialBalance());
        existingEntity.setStatus(account.getStatus());
        existingEntity.setClientId(account.getClientId());
        
        return existingEntity;
    }

    public Account toDomainModel() {
        return new Account(
            this.id,
            this.accountNumber,
            this.accountType,
            this.initialBalance,
            this.status,
            this.clientId
        );
    }
}