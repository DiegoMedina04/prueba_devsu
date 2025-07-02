package ms.cliente_persona.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String CLIENT_EXCHANGE = "client.exchange";
    public static final String CLIENT_CREATED_QUEUE = "client.created.queue";
    public static final String CLIENT_UPDATED_QUEUE = "client.updated.queue";
    public static final String CLIENT_DELETED_QUEUE = "client.deleted.queue";
    
    public static final String CLIENT_CREATED_ROUTING_KEY = "client.created";
    public static final String CLIENT_UPDATED_ROUTING_KEY = "client.updated";
    public static final String CLIENT_DELETED_ROUTING_KEY = "client.deleted";

    @Bean
    public TopicExchange clientExchange() {
        return new TopicExchange(CLIENT_EXCHANGE);
    }

    @Bean
    public Queue clientCreatedQueue() {
        return QueueBuilder.durable(CLIENT_CREATED_QUEUE).build();
    }

    @Bean
    public Queue clientUpdatedQueue() {
        return QueueBuilder.durable(CLIENT_UPDATED_QUEUE).build();
    }

    @Bean
    public Queue clientDeletedQueue() {
        return QueueBuilder.durable(CLIENT_DELETED_QUEUE).build();
    }

    @Bean
    public Binding clientCreatedBinding() {
        return BindingBuilder
                .bind(clientCreatedQueue())
                .to(clientExchange())
                .with(CLIENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding clientUpdatedBinding() {
        return BindingBuilder
                .bind(clientUpdatedQueue())
                .to(clientExchange())
                .with(CLIENT_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Binding clientDeletedBinding() {
        return BindingBuilder
                .bind(clientDeletedQueue())
                .to(clientExchange())
                .with(CLIENT_DELETED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
