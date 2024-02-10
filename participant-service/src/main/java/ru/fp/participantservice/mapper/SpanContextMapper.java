package ru.fp.participantservice.mapper;

import io.jaegertracing.internal.JaegerSpanContext;
import org.springframework.stereotype.Component;
import ru.fp.participantservice.dto.SpanContextDto;

import java.util.function.Function;

@Component
public class SpanContextMapper {
    public static Function<JaegerSpanContext, SpanContextDto> mapSpanContextToDto =
            spanContext -> SpanContextDto.builder()
                    .traceIdHigh(spanContext.getTraceIdHigh())
                    .traceIdLow(spanContext.getTraceIdLow())
                    .parentId(spanContext.getParentId())
                    .spanId(spanContext.getSpanId())
                    .flags(spanContext.getFlags())
                    .build();
}
