package ru.fp.participantservice.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransferDto {
    private String bicCd;
    private String bicDb;
    @Nullable private String codeDb;
    @Nullable private String codeCd;
    private BigDecimal value;
}