package ru.fp.coreservice.mapper;

import ru.fp.coreservice.dto.IncomingMessageDto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessageStatus;

import java.sql.Timestamp;
import java.util.function.Function;

public class IncomingMessageMapper {
    public static Function<IncomingMessageDto, IncomingMessage> MAP_CREATE_TO_INCOMING_MESSAGE =
            source -> {
                IncomingMessage incomingMessage = new IncomingMessage();
                incomingMessage.setCreationDate(new Timestamp(System.currentTimeMillis()));
                incomingMessage.setText(source.getMsg());
                incomingMessage.setStatus(IncomingMessageStatus.NEW);
                return incomingMessage;
            };

    public static Function<IncomingMessage, IncomingMessageDto> MAP_INCOMING_MESSAGE_TO_DTO =
            source -> IncomingMessageDto.builder()
                    .msg(source.getText())
                    .type(source.getType().getCode())
                    .sender(source.getSenderParticipant().getBic())
                    .build();
}
