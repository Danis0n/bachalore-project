package ru.di.receiptservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
public class ReceiptDto {

    private UUID uuid;
    private Timestamp date;
    private BigDecimal amount;
    private String currencyCode;
    private String bicCd;
    private String bicDb;

}
