package ru.fp.participantservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.participantservice.dto.OutgoingMessageDto;
import ru.fp.participantservice.service.OutGoingMessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/outgoing-message")
public class OutgoingMessageController {

    private final OutGoingMessageService outGoingMessageService;

    @GetMapping
    public List<OutgoingMessageDto> findAll() {
        return outGoingMessageService.findAll();
    }

}
