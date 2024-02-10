package ru.fp.participantservice.controller;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.participantservice.dto.OutgoingMessageDto;
import ru.fp.participantservice.dto.TransferDto;
import ru.fp.participantservice.service.TransferService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transfer")
public class TransferController {

    private final TransferService transferService;
    private final Tracer tracer;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody TransferDto dto) {
        Span span = tracer.buildSpan("transfer-create").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            transferService.createTransferRequest(dto);
        }
        catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
    }

    @GetMapping("{id}")
    public OutgoingMessageDto findById(@PathVariable("id") Long id) {
        return transferService.findByIdOrThrow(id);
    }

}
