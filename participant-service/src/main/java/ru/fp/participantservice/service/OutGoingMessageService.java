package ru.fp.participantservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fp.participantservice.dto.OutgoingMessageDto;
import ru.fp.participantservice.mapper.OutgoingMessageMapper;
import ru.fp.participantservice.repository.OutgoingMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutGoingMessageService {

    private final OutgoingMessageRepository outgoingMessageRepository;

    public List<OutgoingMessageDto> findAll() {
        return outgoingMessageRepository.findAll()
                .stream()
                .map(OutgoingMessageMapper.MAP_OUTGOING_MESSAGE_TO_DTO)
                .toList();
    }

}
