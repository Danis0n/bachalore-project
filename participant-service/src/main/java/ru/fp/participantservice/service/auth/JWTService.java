package ru.fp.participantservice.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import ru.fp.participantservice.dto.JWTTokenPair;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Getter
public class JWTService {

    private final JWTVerifier jwtVerifier;
    private final Algorithm algorithm;
    public static final String USER_ROLE = "USER";

    public JWTService(String key) {
        this.algorithm = Algorithm.HMAC256(key.getBytes(StandardCharsets.UTF_8));
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    public JWTTokenPair generatePair(String login, String role) {
        return JWTTokenPair.builder()
                .accessToken(generateAccessToken(login, role))
                .refreshToken(generateRefreshToken(login, role))
                .build();
    }

    private String generateAccessToken(String login, String role) {
        return JWT.create()
                .withSubject(login)
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer("login")
                .withClaim("roles", role)
                .sign(algorithm);
    }

    private String generateRefreshToken(String login, String role) {
        return JWT.create().
                withSubject(login).
                withExpiresAt(new Date(System.currentTimeMillis() + 30 * 30 * 60 * 1000)).
                withIssuer("login").
                withClaim("roles", role).
                sign(algorithm);
    }

}
