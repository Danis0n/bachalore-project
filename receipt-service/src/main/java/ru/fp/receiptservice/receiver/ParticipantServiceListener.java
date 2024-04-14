package ru.fp.receiptservice.receiver;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.fp.receiptservice.dto.ParticipantDto;
import ru.fp.receiptservice.service.ParticipantService;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "broker", name = "profile", havingValue = "rabbit", matchIfMissing = true)
public class ParticipantServiceListener {

    @Value("${destinations.queues.participant.queue}")
    private String queue;

    private final ParticipantService participantService;

    @RabbitListener(queues = "${destinations.queues.participant.queue}")
    public void receiveMessagesFromTopic(ParticipantDto dto) {
        log.info("Message received, queue={}", queue);
        participantService.save(dto);
    }

}
