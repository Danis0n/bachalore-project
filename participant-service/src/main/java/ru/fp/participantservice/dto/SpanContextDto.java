package ru.fp.participantservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpanContextDto {
    long traceIdLow;
    long traceIdHigh;
    long spanId;
    long parentId;
    byte flags;
}
