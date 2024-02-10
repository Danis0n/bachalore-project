package ru.fp.coreservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class Pacs008Dto implements Serializable {
    private String messageId;
    private LocalDateTime dateTime;
    private BigDecimal value;
    private String currency;
    private String bicCd;
    private String bicDb;
    private String codeCd;
    private String codeDb;
    private String endToEndId;
    private String instrId;

}
