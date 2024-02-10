package ru.fp.participantservice.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "broker", name = "profile", havingValue = "rabbit")
public class RabbitConfiguration {

    @Value("${destinations.topics.participant.exchange}")
    private String exchange;

    @Autowired
    private AmqpAdmin amqpAdmin;

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
    public void setupTopicDestinations() {

        log.info("Creating TopicExchange: exchange={}", exchange);

        Exchange ex = ExchangeBuilder
                .topicExchange(exchange)
                .durable(true)
                .build();

        amqpAdmin.declareExchange(ex);

        log.info("Topic Exchange successfully created.");
    }

}
