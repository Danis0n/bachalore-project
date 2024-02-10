package ru.fp.participantservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class ParticipantInfoDto {
    private Long id;
    private String name;
    private String bic;
    private TypeDto type;
    private Timestamp registrationDate;
}