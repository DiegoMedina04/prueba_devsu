package ms.cuenta_movimiento.infrastructure.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.cuenta_movimiento.infrastructure.config.RabbitMQConfig;
import ms.cuenta_movimiento.infrastructure.events.dto.ClientCreatedEvent;
import ms.cuenta_movimiento.infrastructure.events.dto.ClientUpdatedEvent;
import ms.cuenta_movimiento.infrastructure.events.dto.ClientDeletedEvent;
import ms.cuenta_movimiento.infrastructure.external.ClientServiceAdapter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientEventConsumer {

    private final ClientServiceAdapter clientServiceAdapter;

    @RabbitListener(queues = RabbitMQConfig.CLIENT_CREATED_QUEUE)
    public void handleClientCreated(ClientCreatedEvent event) {
        try {
            clientServiceAdapter.addClientToCache(
                    event.getClientId(),
                    event.getName(),
                    event.getIdentification(),
                    event.getStatus()
            );
        } catch (Exception e) {
            System.out.println("Error processing ClientCreatedEvent for client ID: {}"+ event.getClientId()+ e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.CLIENT_UPDATED_QUEUE)
    public void handleClientUpdated(ClientUpdatedEvent event) {
        try {

            clientServiceAdapter.updateClientCache(
                    event.getClientId(),
                    event.getName(),
                    event.getIdentification(),
                    event.getStatus()
            );
        } catch (Exception e) {
            System.out.println("Error processing ClientUpdatedEvent for client ID: {}"+ event.getClientId()+ e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.CLIENT_DELETED_QUEUE)
    public void handleClientDeleted(ClientDeletedEvent event) {
        try {
            clientServiceAdapter.removeFromCache(event.getClientId());
        } catch (Exception e) {
        }
    }
}