package ms.cliente_persona.infrastructure.repositories.person;

import ms.cliente_persona.infrastructure.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPersonRepository extends JpaRepository<PersonEntity, Long> {
}
