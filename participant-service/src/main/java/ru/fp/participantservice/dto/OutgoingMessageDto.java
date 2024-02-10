package ru.fp.participantservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.fp.participantservice.aop.annotaion.AddUuid;

@Getter
@Setter
@Builder
@AddUuid(name = "uuid")
public class OutgoingMessageDto {
    private String sender;
    private String type;
    private String msg;
    private SpanContextDto spanContext;
    private String uuid;
}