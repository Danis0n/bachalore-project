package ru.fp.participantservice.service.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fp.participantservice.dto.JWTTokenPair;
import ru.fp.participantservice.dto.LoginRequest;
import ru.fp.participantservice.dto.LoginResponse;
import ru.fp.participantservice.dto.ParticipantDto;
import ru.fp.participantservice.entity.participant.Participant;
import ru.fp.participantservice.entity.participant.ParticipantCredentials;
import ru.fp.participantservice.exception.RefreshTokenException;
import ru.fp.participantservice.mapper.ParticipantMapper;
import ru.fp.participantservice.service.ParticipantService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ParticipantService participantService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder bcrypt;
    private final JWTService jwtService;

    public LoginResponse login(LoginRequest dto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword());
        authenticationManager.authenticate(token);

        Optional<ParticipantCredentials> participant = participantService
                .findByLogin(dto.getLogin());

        JWTTokenPair jwtTokenPair = jwtService.generatePair(dto.getLogin());
        ParticipantDto participantDto = ParticipantMapper.mapParticipantToDto.apply(participant.get());

        return LoginResponse.builder()
                .tokens(jwtTokenPair)
                .participant(participantDto)
                .build();
    }

    public void register(ParticipantDto dto) {
        Participant participant = participantService.save(dto);

        ParticipantCredentials credentials = new ParticipantCredentials();

        credentials.setLogin(dto.getLogin());
        credentials.setHashedPassword(bcrypt.encode(dto.getPassword()));
        credentials.setParticipant(participant);

        participantService.saveCredentials(credentials);
    }

    public JWTTokenPair refresh(String token) {

        try {

            DecodedJWT verifiedJWT = jwtService.getJwtVerifier().verify(token);
            String subject = verifiedJWT.getSubject();
            return jwtService.generatePair(subject);

        } catch (JWTVerificationException e) {
            throw new RefreshTokenException("refresh token: " + token + " is invalid!");
        }

    }
}
