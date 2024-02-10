package ru.fp.participantservice.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fp.participantservice.dto.JWTTokenPair;
import ru.fp.participantservice.dto.LoginRequest;
import ru.fp.participantservice.dto.LoginResponse;
import ru.fp.participantservice.dto.ParticipantDto;
import ru.fp.participantservice.service.auth.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {
        return ResponseEntity.ok(authenticationService.login(login));
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody ParticipantDto dto) {
        authenticationService.register(dto);
    }

    @PostMapping("refresh")
    public ResponseEntity<JWTTokenPair> refresh(@RequestParam("token") String token) {
        return ResponseEntity.ok(authenticationService.refresh(token));
    }

}
