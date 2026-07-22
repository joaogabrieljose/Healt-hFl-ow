package jjose.dev.com.scheduling.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String HEALTHFLOW_EXCHANGE = "healthflow.exchange";

    public static final String APPOINTMENT_CREATED_QUEUE =
            "notification.appointment.created.queue";

    public static final String APPOINTMENT_CREATED_ROUTING_KEY =
            "appointment.created";

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public TopicExchange healthFlowExchange() {
        return new TopicExchange(
                HEALTHFLOW_EXCHANGE,
                true,
                false
        );
    }

    @Bean
    public Queue appointmentCreatedQueue() {
        return new Queue(
                APPOINTMENT_CREATED_QUEUE,
                true
        );
    }

    @Bean
    public Binding appointmentCreatedBinding(
            Queue appointmentCreatedQueue,
            TopicExchange healthFlowExchange
    ) {
        return BindingBuilder
                .bind(appointmentCreatedQueue)
                .to(healthFlowExchange)
                .with(APPOINTMENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);

        return rabbitTemplate;
    }
}