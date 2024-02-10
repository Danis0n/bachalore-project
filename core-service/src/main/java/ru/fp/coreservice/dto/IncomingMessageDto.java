package ru.fp.coreservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncomingMessageDto {
    private String sender;
    private String type;
    private String msg;
    private SpanContextDto spanContext;
    private String uuid;
}
