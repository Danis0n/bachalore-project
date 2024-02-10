package ru.fp.coreservice.rabbit.receiver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.ParticipantDto;
import ru.fp.coreservice.service.ParticipantService;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "broker", name = "profile", havingValue = "rabbit", matchIfMissing = true)
public class ParticipantServiceListener {

    private final ParticipantService participantService;

    @RabbitListener(queues = "${destinations.queues.participant.queue}")
    public void receiveParticipant(ParticipantDto participantDto) {
        log.info("Message 'create participant' received, bic={}", participantDto.getBic());
        participantService.save(participantDto);
    }

}
