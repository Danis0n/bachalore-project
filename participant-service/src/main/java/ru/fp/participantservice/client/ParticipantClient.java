package ru.fp.participantservice.client;

import lombok.NonNull;
import ru.fp.participantservice.aop.annotaion.RequestUuid;
import ru.fp.participantservice.dto.OutgoingMessageDto;
import ru.fp.participantservice.dto.ParticipantDto;

public interface ParticipantClient {

    @RequestUuid
    void sendParticipantRequest(
            @NonNull ParticipantDto participantDto
    ) throws Exception;

    @RequestUuid
    void sendOutgoingMessageRequest(
            @NonNull OutgoingMessageDto outgoingMessageDto
    ) throws Exception;

}
