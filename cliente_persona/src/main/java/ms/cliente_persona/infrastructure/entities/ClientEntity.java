package ms.cliente_persona.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.cliente_persona.domain.models.Client;
import ms.cliente_persona.domain.models.Person;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private PersonEntity personEntity;
    private String password;
    private Boolean status;

    public static ClientEntity fromDomainModel(Client client) {
        PersonEntity person = null;
        if (client.getPersona() != null) {
            person = PersonEntity.fromDomainModel(client.getPersona());
        }
        
        return new ClientEntity(
                client.getId(),
                person,
                client.getPassword(),
                client.getStatus()
        );
    }
    
    public static ClientEntity fromDomainModelForUpdate(Client client, ClientEntity existingEntity) {
        PersonEntity person = existingEntity.getPersonEntity();
        
        if (client.getPersona() != null && person != null) {
            person.setName(client.getPersona().getName());
            person.setGender(client.getPersona().getGender());
            person.setAge(client.getPersona().getAge());
            person.setIdentification(client.getPersona().getIdentification());
            person.setAddress(client.getPersona().getAddress());
            person.setTelephone(client.getPersona().getTelephone());
        } else if (client.getPersona() != null) {
            person = PersonEntity.fromDomainModel(client.getPersona());
        }
        
        existingEntity.setPersonEntity(person);
        existingEntity.setPassword(client.getPassword());
        existingEntity.setStatus(client.getStatus());
        
        return existingEntity;
    }

    public Client toDomainModel() {
        Person person = this.personEntity != null ? this.personEntity.toDomainModel() : null;
        
        return new Client(
            this.id,
            person,
            this.password,
            this.status
        );
    }
}
