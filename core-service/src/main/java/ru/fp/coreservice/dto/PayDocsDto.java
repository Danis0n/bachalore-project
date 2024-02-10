package ru.fp.coreservice.dto;

import lombok.Builder;
import lombok.Getter;
import ru.fp.coreservice.entity.paydoc.PayDocsStep;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class PayDocsDto {

    private UUID guid;

    private String debitAcc;
    private String creditAcc;

    private BigDecimal amount;

    private PayDocsStep step;

    private Timestamp valueDate;

    private String participantBic;

    private Timestamp startTime;
    private Timestamp finishTime;

    private String currencyCode;
}
