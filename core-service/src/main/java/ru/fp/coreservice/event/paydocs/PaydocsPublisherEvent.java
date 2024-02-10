package ru.fp.coreservice.event.paydocs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaydocsPublisherEvent {

    private final ApplicationEventPublisher publisher;

    public void publishPaydocsCreate(Pacs008Dto pacs008Dto, IncomingMessage incomingMessage) {
        PayDocsCreateEvent event = new PayDocsCreateEvent(pacs008Dto, incomingMessage);
        log.info("CreatePaydocs event was created: " + pacs008Dto.getMessageId());
        publisher.publishEvent(event);
    }

}
