package ru.fp.coreservice.dto;


import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class TransactionDto {
    private String creditAccount;
    private String debitAccount;
    private String currency;
    private String amount;
    private Timestamp time;
}
