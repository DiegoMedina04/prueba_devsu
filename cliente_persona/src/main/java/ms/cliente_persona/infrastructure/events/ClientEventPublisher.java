package ms.cliente_persona.infrastructure.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.cliente_persona.infrastructure.config.RabbitMQConfig;
import ms.cliente_persona.infrastructure.events.dto.ClientCreatedEvent;
import ms.cliente_persona.infrastructure.events.dto.ClientUpdatedEvent;
import ms.cliente_persona.infrastructure.events.dto.ClientDeletedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientEventPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    
    public void publishClientCreated(ClientCreatedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLIENT_EXCHANGE,
                    RabbitMQConfig.CLIENT_CREATED_ROUTING_KEY,
                    event
            );
            log.info("Published ClientCreatedEvent for client ID: {}", event.getClientId());
        } catch (Exception e) {
            log.error("Error publishing ClientCreatedEvent for client ID: {}", event.getClientId(), e);
        }
    }
    
    public void publishClientUpdated(ClientUpdatedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLIENT_EXCHANGE,
                    RabbitMQConfig.CLIENT_UPDATED_ROUTING_KEY,
                    event
            );
            log.info("Published ClientUpdatedEvent for client ID: {}", event.getClientId());
        } catch (Exception e) {
            log.error("Error publishing ClientUpdatedEvent for client ID: {}", event.getClientId(), e);
        }
    }
    
    public void publishClientDeleted(ClientDeletedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLIENT_EXCHANGE,
                    RabbitMQConfig.CLIENT_DELETED_ROUTING_KEY,
                    event
            );
            log.info("Published ClientDeletedEvent for client ID: {}", event.getClientId());
        } catch (Exception e) {
            log.error("Error publishing ClientDeletedEvent for client ID: {}", event.getClientId(), e);
        }
    }
}
