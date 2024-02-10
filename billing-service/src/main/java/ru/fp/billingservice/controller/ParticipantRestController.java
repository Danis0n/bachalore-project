package ru.fp.billingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fp.billingservice.dto.ParticipantDto;
import ru.fp.billingservice.entity.Participant;
import ru.fp.billingservice.service.ParticipantService;

@RestController
@RequestMapping("api/participant")
@RequiredArgsConstructor
public class ParticipantRestController {

    private final ParticipantService participantService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public boolean create(final @RequestBody ParticipantDto dto) {
        participantService.save(dto);
        return true;
    }

    @GetMapping("info/{bic}")
    public ResponseEntity<Participant> create(final @PathVariable String bic) {
        return participantService.findByBic(bic)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
