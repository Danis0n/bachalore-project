package ru.fp.coreservice.mapper;

import io.jaegertracing.internal.JaegerSpanContext;
import io.opentracing.SpanContext;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.SpanContextDto;

import java.util.function.Function;

@Component
public class SpanContextMapper {
    public static Function<SpanContextDto, SpanContext> mapSpanContextFromDto =
            spanContextDto -> new JaegerSpanContext(
                    spanContextDto.getTraceIdHigh(),
                    spanContextDto.getTraceIdLow(),
                    spanContextDto.getSpanId(),
                    spanContextDto.getParentId(),
                    spanContextDto.getFlags()
            );
}