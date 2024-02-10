package ru.fp.coreservice.event.transaction;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.transaction.Transaction;

@Getter
public class TransactionExecutionEvent extends ApplicationEvent {

    private final Transaction transaction;
    private final String accountCdCode;
    private final String accountDbCode;
    private final IncomingMessage incomingMessage;

    public TransactionExecutionEvent(Pacs008Dto pacs008, Transaction transaction,
                                     String accountCdCode, String accountDbCode,
                                     IncomingMessage incomingMessage) {
        super(pacs008);
        this.transaction = transaction;
        this.accountCdCode = accountCdCode;
        this.accountDbCode = accountDbCode;
        this.incomingMessage = incomingMessage;
    }
}
