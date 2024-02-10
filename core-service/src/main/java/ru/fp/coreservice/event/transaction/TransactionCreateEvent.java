package ru.fp.coreservice.event.transaction;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.paydoc.PayDoc;

@Getter
public class TransactionCreateEvent extends ApplicationEvent {

    private final PayDoc payDoc;
    private final IncomingMessage incomingMessage;

    public TransactionCreateEvent(Pacs008Dto pacs008, PayDoc payDoc, IncomingMessage incomingMessage) {
        super(pacs008);
        this.payDoc = payDoc;
        this.incomingMessage = incomingMessage;
    }
}
