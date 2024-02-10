package ru.fp.coreservice.event.paydocs;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;

@Getter
public class PayDocsCreateEvent extends ApplicationEvent {

    private final IncomingMessage incomingMessage;

    public PayDocsCreateEvent(Pacs008Dto pacs008, IncomingMessage incomingMessage) {
        super(pacs008);
        this.incomingMessage = incomingMessage;
    }
}
