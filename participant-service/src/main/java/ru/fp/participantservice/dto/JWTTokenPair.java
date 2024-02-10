package ru.fp.participantservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JWTTokenPair {
    private String accessToken;
    private String refreshToken;
}
