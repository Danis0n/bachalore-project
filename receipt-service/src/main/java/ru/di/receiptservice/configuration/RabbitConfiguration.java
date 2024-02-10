package ru.di.receiptservice.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "broker", name = "profile", havingValue = "rabbit", matchIfMissing = true)
public class RabbitConfiguration {

    private final AmqpAdmin amqpAdmin;

    @Value("${destinations.topics.participant.exchange}")
    private String exchange;

    @Value("${destinations.queues.participant.queue}")
    private String queue;

    @Value("${destinations.queues.participant.routing-key}")
    private String routingKey;

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setChannelTransacted(true);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @PostConstruct
    public void setupQueueDestinations() {

        log.info("Creating directExchange: name={}, queue={}, routingKey={}", exchange, queue, routingKey);

        Exchange ex = ExchangeBuilder
                .topicExchange(exchange)
                .durable(true)
                .build();

        amqpAdmin.declareExchange(ex);

        Queue q = QueueBuilder
                .nonDurable(queue)
                .build();

        amqpAdmin.declareQueue(q);

        Binding b = BindingBuilder.bind(q)
                .to(ex)
                .with(routingKey)
                .noargs();

        amqpAdmin.declareBinding(b);

    }
}