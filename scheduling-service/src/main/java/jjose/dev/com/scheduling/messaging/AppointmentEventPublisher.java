package jjose.dev.com.scheduling.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import jjose.dev.com.scheduling.config.RabbitMQConfig;
import jjose.dev.com.scheduling.events.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppointmentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishAppointmentCreated(AppointmentCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.HEALTHFLOW_EXCHANGE,
                RabbitMQConfig.APPOINTMENT_CREATED_ROUTING_KEY,
                event
        );
    }
}