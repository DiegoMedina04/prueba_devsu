package ms.cliente_persona.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Client Domain Entity Tests")
class ClientTest {

    private Person person;
    private Client client;

    @BeforeEach
    void setUp() {
        person = new Person(
                1L,
                "Jose Lema",
                "Masculino",
                35,
                "1234567890",
                "Otavalo sn y principal",
                "098254785"
        );
        
        client = new Client(
                1L,
                person,
                "1234",
                true
        );
    }

    @Test
    @DisplayName("Should create Client with valid data")
    void shouldCreateClientWithValidData() {
        assertNotNull(client);
        assertEquals(1L, client.getId());
        assertEquals(person, client.getPersona());
        assertEquals("1234", client.getPassword());
        assertTrue(client.getStatus());
    }

    @Test
    @DisplayName("Should allow null ID for new clients")
    void shouldAllowNullIdForNewClients() {
        Client newClient = new Client(null, person, "1234", true);
        
        assertNotNull(newClient);
        assertNull(newClient.getId());
        assertEquals(person, newClient.getPersona());
    }

    @Test
    @DisplayName("Should handle inactive client status")
    void shouldHandleInactiveClientStatus() {
        Client inactiveClient = new Client(1L, person, "1234", false);
        
        assertNotNull(inactiveClient);
        assertFalse(inactiveClient.getStatus());
    }

    @Test
    @DisplayName("Should allow password updates")
    void shouldAllowPasswordUpdates() {
        String newPassword = "newPassword123";
        
        client.setPassword(newPassword);
        
        assertEquals(newPassword, client.getPassword());
    }

    @Test
    @DisplayName("Should allow status updates")
    void shouldAllowStatusUpdates() {
        assertTrue(client.getStatus());
        
        client.setStatus(false);
        
        assertFalse(client.getStatus());
    }

    @Test
    @DisplayName("Should maintain Person reference integrity")
    void shouldMaintainPersonReferenceIntegrity() {
        Person newPerson = new Person(
                2L,
                "Marianela Montalvo",
                "Femenino",
                28,
                "0987654321",
                "Amazonas y NNUU",
                "097548965"
        );
        
        client.setPersona(newPerson);
        
        assertEquals(newPerson, client.getPersona());
        assertEquals("Marianela Montalvo", client.getPersona().getName());
        assertEquals("0987654321", client.getPersona().getIdentification());
    }

    @Test
    @DisplayName("Should implement proper equals and hashCode")
    void shouldImplementProperEqualsAndHashCode() {
        Client client1 = new Client(1L, person, "1234", true);
        Client client2 = new Client(1L, person, "1234", true);
        Client client3 = new Client(2L, person, "1234", true);
        
        assertEquals(client1, client2);
        assertNotEquals(client1, client3);
        assertEquals(client1.hashCode(), client2.hashCode());
    }

    @Test
    @DisplayName("Should handle null Person gracefully")
    void shouldHandleNullPersonGracefully() {
        Client clientWithNullPerson = new Client(1L, null, "1234", true);
        
        assertNotNull(clientWithNullPerson);
        assertNull(clientWithNullPerson.getPersona());
    }

    @Test
    @DisplayName("Should handle empty password")
    void shouldHandleEmptyPassword() {
        Client clientWithEmptyPassword = new Client(1L, person, "", true);
        
        assertNotNull(clientWithEmptyPassword);
        assertEquals("", clientWithEmptyPassword.getPassword());
    }

    @Test
    @DisplayName("Should handle null password")
    void shouldHandleNullPassword() {
        Client clientWithNullPassword = new Client(1L, person, null, true);
        
        assertNotNull(clientWithNullPassword);
        assertNull(clientWithNullPassword.getPassword());
    }

    @Test
    @DisplayName("Should provide proper toString representation")
    void shouldProvideProperToStringRepresentation() {
        String clientString = client.toString();
        
        assertNotNull(clientString);
        assertTrue(clientString.contains("Client"));
        assertTrue(clientString.contains("1"));
        assertTrue(clientString.contains("1234"));
    }

    @Test
    @DisplayName("Should support no-args constructor")
    void shouldSupportNoArgsConstructor() {
        Client emptyClient = new Client();
        
        assertNotNull(emptyClient);
        assertNull(emptyClient.getId());
        assertNull(emptyClient.getPersona());
        assertNull(emptyClient.getPassword());
        assertNull(emptyClient.getStatus());
    }

    @Test
    @DisplayName("Should handle Business Logic - Client Inheritance from Person")
    void shouldHandleBusinessLogicClientInheritanceFromPerson() {
        assertEquals("Jose Lema", client.getPersona().getName());
        assertEquals("Masculino", client.getPersona().getGender());
        assertEquals(35, client.getPersona().getAge());
        assertEquals("1234567890", client.getPersona().getIdentification());
        assertEquals("Otavalo sn y principal", client.getPersona().getAddress());
        assertEquals("098254785", client.getPersona().getTelephone());
        
        assertEquals("1234", client.getPassword());
        assertTrue(client.getStatus());
    }

    @Test
    @DisplayName("Should support Client ID as unique identifier")
    void shouldSupportClientIdAsUniqueIdentifier() {
        Client client1 = new Client(100L, person, "1234", true);
        Client client2 = new Client(200L, person, "1234", true);
        
        assertNotEquals(client1.getId(), client2.getId());
        assertEquals(100L, client1.getId());
        assertEquals(200L, client2.getId());
    }
}