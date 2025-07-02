package ms.cliente_persona.application.useCases.client;

import ms.cliente_persona.domain.exceptions.BadRequestException;
import ms.cliente_persona.domain.models.Client;
import ms.cliente_persona.domain.models.Person;
import ms.cliente_persona.domain.ports.out.client.ClientRepositoryPort;
import ms.cliente_persona.infrastructure.events.ClientEventPublisher;
import ms.cliente_persona.infrastructure.events.dto.ClientCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateClientUseCase Tests")
class CreateClientUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;
    
    @Mock
    private ClientEventPublisher eventPublisher;
    
    private CreateClientUseCaseImpl createClientUseCase;
    
    private Person person;
    private Client client;

    @BeforeEach
    void setUp() {
        createClientUseCase = new CreateClientUseCaseImpl(clientRepositoryPort, eventPublisher);
        
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
                null, // New client without ID
                person,
                "1234",
                true
        );
    }

    @Test
    @DisplayName("Should create client successfully")
    void shouldCreateClientSuccessfully() {
        Client savedClient = new Client(1L, person, "1234", true);
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(savedClient);
        
        Client result = createClientUseCase.save(client);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jose Lema", result.getPersona().getName());
        assertEquals("1234", result.getPassword());
        assertTrue(result.getStatus());
        
        verify(clientRepositoryPort).save(client);
        verify(eventPublisher).publishClientCreated(any(ClientCreatedEvent.class));
    }

    @Test
    @DisplayName("Should publish ClientCreatedEvent with correct data")
    void shouldPublishClientCreatedEventWithCorrectData() {
        Client savedClient = new Client(1L, person, "1234", true);
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(savedClient);
        
        ArgumentCaptor<ClientCreatedEvent> eventCaptor = ArgumentCaptor.forClass(ClientCreatedEvent.class);
        
        createClientUseCase.save(client);
        
        verify(eventPublisher).publishClientCreated(eventCaptor.capture());
        
        ClientCreatedEvent capturedEvent = eventCaptor.getValue();
        assertEquals(1L, capturedEvent.getClientId());
        assertEquals("Jose Lema", capturedEvent.getName());
        assertEquals("1234567890", capturedEvent.getIdentification());
        assertEquals("Masculino", capturedEvent.getGender());
        assertEquals(35, capturedEvent.getAge());
        assertEquals("Otavalo sn y principal", capturedEvent.getAddress());
        assertEquals("098254785", capturedEvent.getPhone());
        assertTrue(capturedEvent.getStatus());
    }



    @Test
    @DisplayName("Should handle client with inactive status")
    void shouldHandleClientWithInactiveStatus() {
        Client inactiveClient = new Client(null, person, "1234", false);
        Client savedInactiveClient = new Client(1L, person, "1234", false);
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(savedInactiveClient);
        
        Client result = createClientUseCase.save(inactiveClient);
        
        assertNotNull(result);
        assertFalse(result.getStatus());
        verify(clientRepositoryPort).save(inactiveClient);
        verify(eventPublisher).publishClientCreated(any(ClientCreatedEvent.class));
    }

    @Test
    @DisplayName("Should handle different person data")
    void shouldHandleDifferentPersonData() {
        Person differentPerson = new Person(
                2L,
                "Marianela Montalvo",
                "Femenino",
                28,
                "0987654321",
                "Amazonas y NNUU",
                "097548965"
        );
        Client clientWithDifferentPerson = new Client(null, differentPerson, "5678", true);
        Client savedClient = new Client(2L, differentPerson, "5678", true);
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(savedClient);
        
        Client result = createClientUseCase.save(clientWithDifferentPerson);
        
        assertNotNull(result);
        assertEquals("Marianela Montalvo", result.getPersona().getName());
        assertEquals("Femenino", result.getPersona().getGender());
        assertEquals(28, result.getPersona().getAge());
        assertEquals("0987654321", result.getPersona().getIdentification());
        
        verify(clientRepositoryPort).save(clientWithDifferentPerson);
        verify(eventPublisher).publishClientCreated(any(ClientCreatedEvent.class));
    }

    @Test
    @DisplayName("Should call repository save exactly once")
    void shouldCallRepositorySaveExactlyOnce() {
        Client savedClient = new Client(1L, person, "1234", true);
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(savedClient);
        
        createClientUseCase.save(client);
        
        verify(clientRepositoryPort, times(1)).save(client);
    }

    @Test
    @DisplayName("Should call event publisher exactly once")
    void shouldCallEventPublisherExactlyOnce() {
        Client savedClient = new Client(1L, person, "1234", true);
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(savedClient);
        
        createClientUseCase.save(client);
        
        verify(eventPublisher, times(1)).publishClientCreated(any(ClientCreatedEvent.class));
    }

    @Test
    @DisplayName("Should preserve all client data during save")
    void shouldPreserveAllClientDataDuringSave() {
        Client savedClient = new Client(1L, person, "complexPassword123", true);
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(savedClient);
        
        Client clientToSave = new Client(null, person, "complexPassword123", true);
        
        Client result = createClientUseCase.save(clientToSave);
        
        assertEquals("complexPassword123", result.getPassword());
        assertEquals(person.getName(), result.getPersona().getName());
        assertEquals(person.getIdentification(), result.getPersona().getIdentification());
        assertEquals(person.getAddress(), result.getPersona().getAddress());
        assertEquals(person.getTelephone(), result.getPersona().getTelephone());
    }

    @Test
    @DisplayName("Should work with minimum required data")
    void shouldWorkWithMinimumRequiredData() {
        Person minimalPerson = new Person(null, "Name", null, null, "ID123", null, null);
        Client minimalClient = new Client(null, minimalPerson, "pass", true);
        Client savedMinimalClient = new Client(1L, minimalPerson, "pass", true);
        
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(savedMinimalClient);
        
        Client result = createClientUseCase.save(minimalClient);
        
        assertNotNull(result);
        assertEquals("Name", result.getPersona().getName());
        assertEquals("ID123", result.getPersona().getIdentification());
        assertEquals("pass", result.getPassword());
        
        verify(clientRepositoryPort).save(minimalClient);
        verify(eventPublisher).publishClientCreated(any(ClientCreatedEvent.class));
    }
}