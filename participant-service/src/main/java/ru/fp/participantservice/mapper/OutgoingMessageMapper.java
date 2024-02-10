package ru.fp.participantservice.mapper;

import ru.fp.participantservice.dto.OutgoingMessageDto;
import ru.fp.participantservice.entity.outgoing.OutgoingMessage;

import java.util.function.Function;

public class OutgoingMessageMapper {

    public static Function<OutgoingMessage, OutgoingMessageDto> MAP_OUTGOING_MESSAGE_TO_DTO =
            message -> OutgoingMessageDto
                    .builder()
                    .sender(message.getSender().getBic())
                    .type(message.getType().name())
                    .msg(message.getText())
                    .build();
}
