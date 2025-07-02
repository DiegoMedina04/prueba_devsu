package ms.cliente_persona.infrastructure.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.cliente_persona.domain.models.Person;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persona")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String telephone;

    public static PersonEntity fromDomainModel(Person person) {

        return new PersonEntity(
                person.getId(),
                person.getName(),
                person.getGender(),
                person.getAge(),
                person.getIdentification(),
                person.getAddress(),
                person.getTelephone()
        );
    }

    public Person toDomainModel() {
        return new Person(
            this.id,
            this.name,
            this.gender,
            this.age,
            this.identification,
            this.address,
            this.telephone
        );
    }
}
