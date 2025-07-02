package ms.cuenta_movimiento.infrastructure.repositories.movement;

import ms.cuenta_movimiento.infrastructure.entities.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaMovementRepository extends JpaRepository<MovementEntity, Long> {
    List<MovementEntity> findByAccountId(Long accountId);
    
    @Query("SELECT m FROM MovementEntity m WHERE m.account.id = :accountId AND DATE(m.date) BETWEEN :startDate AND :endDate ORDER BY m.date DESC")
    List<MovementEntity> findByAccountIdAndDateRange(@Param("accountId") Long accountId, 
                                                     @Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);
    
    @Query("SELECT m FROM MovementEntity m WHERE m.account.clientId = :clientId AND DATE(m.date) BETWEEN :startDate AND :endDate ORDER BY m.date DESC")
    List<MovementEntity> findByClientIdAndDateRange(@Param("clientId") Long clientId, 
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);
}