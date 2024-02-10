package ru.fp.coreservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class SpanContextDto implements Serializable {
    long traceIdLow;
    long traceIdHigh;
    long spanId;
    long parentId;
    byte flags;
}
