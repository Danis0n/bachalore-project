package ru.fp.coreservice.controller;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.coreservice.dto.IncomingMessageDto;
import ru.fp.coreservice.mapper.SpanContextMapper;
import ru.fp.coreservice.service.IncomingMessageService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/incoming-message")
@RequiredArgsConstructor
public class IncomingMessageController {
    private final IncomingMessageService incomingMessageService;
    private final Tracer tracer;

    @GetMapping
    public ResponseEntity<List<IncomingMessageDto>> getAll() {
        return ResponseEntity.ok(incomingMessageService.getAllIncomingMessages());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody IncomingMessageDto dto) {
        SpanContext extractedContext = SpanContextMapper.mapSpanContextFromDto.apply(dto.getSpanContext());
        Span span = tracer.buildSpan("incoming-message-controller-create").asChildOf(extractedContext).start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            span.setTag("IncomingMessageUuid", dto.getUuid());

            incomingMessageService.receivePaymentRequest(dto);
        }
        catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
    }

    @PostMapping("test")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTest() {

        IncomingMessageDto dto = IncomingMessageDto.builder()

                .build();

        incomingMessageService.receivePaymentRequest(dto);
    }

}
