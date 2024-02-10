package ru.fp.participantservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrencyDto {
    private Long id;
    private String code;
    private String description;
}