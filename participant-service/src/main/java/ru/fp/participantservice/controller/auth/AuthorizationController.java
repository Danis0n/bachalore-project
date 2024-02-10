package ru.fp.participantservice.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authorization")
public class AuthorizationController {

    @PostMapping("validate")
    public ResponseEntity<Boolean> validate() {
        return ResponseEntity.ok(true);
    }

}
