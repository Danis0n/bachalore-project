package ru.fp.coreservice.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountBalanceDto {

    String name;
    String code;
    String currencyName;

    BigDecimal creditBalance;
    BigDecimal debitBalance;
    BigDecimal value;
    Boolean isActive;
}
