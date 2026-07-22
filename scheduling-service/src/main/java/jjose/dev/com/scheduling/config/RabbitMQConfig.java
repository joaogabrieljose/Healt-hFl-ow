package jjose.dev.com.scheduling.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String HEALTHFLOW_EXCHANGE = "healthflow.exchange";

    @Bean
    public TopicExchange healthFlowExchange() {
        return new TopicExchange(HEALTHFLOW_EXCHANGE);
    }
}