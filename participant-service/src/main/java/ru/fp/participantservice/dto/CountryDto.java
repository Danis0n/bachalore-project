package ru.fp.participantservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CountryDto {
    private Long id;
    private String name;
    private String code;
}
