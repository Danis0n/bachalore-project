package ru.fp.receiptservice.controller;

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
import ru.fp.receiptservice.dto.ParticipantDto;
import ru.fp.receiptservice.entity.Participant;
import ru.fp.receiptservice.service.ParticipantService;


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
    public ResponseEntity<Participant> getInfoByBic(final @PathVariable String bic) {
        return participantService.findByBic(bic)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
