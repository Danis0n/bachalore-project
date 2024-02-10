package ru.fp.participantservice.client;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.fp.participantservice.dto.OutgoingMessageDto;
import ru.fp.participantservice.dto.ParticipantDto;

@Service
@Slf4j
@ConditionalOnProperty(prefix = "broker", name = "profile", havingValue = "rabbit")
public class RabbitClient implements ParticipantClient {

    @Autowired
    private AmqpTemplate template;

    @Value("${destinations.topics.participant.exchange}")
    private String exchange;

    @Value("${destinations.topics.participant.routing-key}")
    private String routingKey;

    public RabbitClient(AmqpTemplate template) {
        this.template = template;
    }

    public void sendParticipantRequest(
            @NonNull ParticipantDto participantDto
    ) throws Exception {

        try {
            log.info("sendMessageToTopic: topic={}, routingKey={}", exchange, routingKey);
            template.convertAndSend(exchange, routingKey, participantDto);

        } catch(AmqpException ex) {
            log.info("Connection refused. Problem thrown when trying to connecto the RabbitMQ");
            throw new Exception(ex.getMessage());
        }

    }

    @Override
    public void sendOutgoingMessageRequest(@NonNull OutgoingMessageDto outgoingMessageDto) throws Exception {
        throw new NotImplementedException();
    }

}
