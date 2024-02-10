package ru.fp.coreservice.event.transaction;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.transaction.Transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
public class TransactionNotificationEvent extends ApplicationEvent {

    private final Transaction transaction;
    private final String accountCdCode;
    private final String accountDbCode;

    public TransactionNotificationEvent(Pacs008Dto pacs008, Transaction transaction, String accountCdCode,
                                        String accountDbCode) {
        super(pacs008);
        this.transaction = transaction;
        this.accountCdCode = accountCdCode;
        this.accountDbCode = accountDbCode;
    }
}
