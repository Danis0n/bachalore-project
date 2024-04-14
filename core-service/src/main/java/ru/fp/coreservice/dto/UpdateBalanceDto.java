package ru.fp.coreservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class UpdateBalanceDto {

    private String bic;
    private String accountCode;
    private BigDecimal value;
}
