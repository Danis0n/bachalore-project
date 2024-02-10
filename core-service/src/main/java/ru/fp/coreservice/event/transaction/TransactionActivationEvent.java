package ru.fp.coreservice.event.transaction;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.transaction.Transaction;

@Getter
public class TransactionActivationEvent extends ApplicationEvent {

    private final Transaction transaction;
    private final IncomingMessage incomingMessage;

    public TransactionActivationEvent(Pacs008Dto pacs008, Transaction transaction, IncomingMessage incomingMessage) {
        super(pacs008);
        this.transaction = transaction;
        this.incomingMessage = incomingMessage;
    }
}
