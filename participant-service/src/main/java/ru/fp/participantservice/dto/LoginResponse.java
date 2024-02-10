package ru.fp.participantservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private JWTTokenPair tokens;
    private ParticipantDto participant;
}
