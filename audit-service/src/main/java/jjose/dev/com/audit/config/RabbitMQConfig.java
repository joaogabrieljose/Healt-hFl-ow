package jjose.dev.com.audit.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String HEALTHFLOW_EXCHANGE = "healthflow.exchange";

    public static final String AUDIT_APPOINTMENT_CREATED_QUEUE =
            "audit.appointment.created.queue";

    public static final String AUDIT_APPOINTMENT_STATUS_CHANGED_QUEUE =
            "audit.appointment.status.changed.queue";

    public static final String APPOINTMENT_CREATED_ROUTING_KEY =
            "appointment.created";

    public static final String APPOINTMENT_STATUS_CHANGED_ROUTING_KEY =
            "appointment.status.changed";

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public TopicExchange healthFlowExchange() {
        return new TopicExchange(HEALTHFLOW_EXCHANGE, true, false);
    }

    @Bean
    public Queue auditAppointmentCreatedQueue() {
        return new Queue(AUDIT_APPOINTMENT_CREATED_QUEUE, true);
    }

    @Bean
    public Queue auditAppointmentStatusChangedQueue() {
        return new Queue(AUDIT_APPOINTMENT_STATUS_CHANGED_QUEUE, true);
    }

    @Bean
    public Binding auditAppointmentCreatedBinding(
            Queue auditAppointmentCreatedQueue,
            TopicExchange healthFlowExchange
    ) {
        return BindingBuilder
                .bind(auditAppointmentCreatedQueue)
                .to(healthFlowExchange)
                .with(APPOINTMENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding auditAppointmentStatusChangedBinding(
            Queue auditAppointmentStatusChangedQueue,
            TopicExchange healthFlowExchange
    ) {
        return BindingBuilder
                .bind(auditAppointmentStatusChangedQueue)
                .to(healthFlowExchange)
                .with(APPOINTMENT_STATUS_CHANGED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);

        return factory;
    }
}