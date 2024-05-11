package ru.fp.participantservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.participantservice.dto.ParticipantDto;
import ru.fp.participantservice.dto.ParticipantInfoDto;
import ru.fp.participantservice.service.ParticipantService;

import java.util.List;

@RestController
@RequestMapping("api/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ParticipantDto dto) {
        participantService.save(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ParticipantInfoDto> findAll() {
        return participantService.findAll();
    }

    @GetMapping("info/{bic}")
    @ResponseStatus(HttpStatus.OK)
    public ParticipantInfoDto findByBic(@PathVariable String bic) {
        return participantService.findByBic(bic);
    }

    @PostMapping("/manage-lock/{isLock}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean manageParticipantLock(@PathVariable Boolean isLock) {
        return participantService.manageParticipantLock(isLock);
    }

}
