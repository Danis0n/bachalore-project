package ru.fp.coreservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.coreservice.dto.ParticipantDto;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.service.ParticipantService;

@RestController
@RequestMapping("api/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean create(@RequestBody ParticipantDto dto) {
        participantService.save(dto);
        return true;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("info/{bic}")
    public ResponseEntity<Participant> findByBic(final @PathVariable String bic) {
        return ResponseEntity.ok(participantService.findParticipantByBicOrThrow(bic));
    }

}
