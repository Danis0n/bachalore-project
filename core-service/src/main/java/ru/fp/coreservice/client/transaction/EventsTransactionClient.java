package ru.fp.coreservice.client.transaction;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.event.paydocs.PaydocsPublisherEvent;

import java.util.Map;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "transaction", name = "profile", havingValue = "events", matchIfMissing = true)
public class EventsTransactionClient implements TransactionClient {

    private final PaydocsPublisherEvent paydocsPublisherEvent;
    private final Tracer tracer;

    @Override
    public void proceedTransaction(Pacs008Dto pacs008, IncomingMessage incomingMessage) {
        Span span = tracer.buildSpan("events-proceed-transaction").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            paydocsPublisherEvent.publishPaydocsCreate(pacs008, incomingMessage);
        }
        catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
    }
}
