package ru.fp.coreservice.event.paydocs;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.event.transaction.TransactionCreateEvent;
import ru.fp.coreservice.service.PayDocsService;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayDocsListener {

    private final PayDocsService payDocsService;
    private final Tracer tracer;

    @EventListener
    public GuidCreateEvent CreatePaydocsHandler(PayDocsCreateEvent event) {
        Pacs008Dto pacs008;
        IncomingMessage incomingMessage;
        PayDoc payDoc;

        Span span = tracer.buildSpan("create-paydocs-handler").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("Event received: CreatePaydocs");
            pacs008 = (Pacs008Dto) event.getSource();
            incomingMessage = event.getIncomingMessage();

            payDoc = payDocsService.createPaydocHandler(pacs008, incomingMessage);
        }
        catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
        return new GuidCreateEvent(pacs008, payDoc, incomingMessage);
    }

    @EventListener
    public TransactionCreateEvent GuidCreateHandler(GuidCreateEvent event) {
        PayDoc payDoc;
        Span span = tracer.buildSpan("guid-create-handler").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("Event received: GuidCreate");
            payDoc = event.getPayDoc();
            payDocsService.GuidCreateHandler(payDoc);
        }
            catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
        return new TransactionCreateEvent((Pacs008Dto) event.getSource(), payDoc, event.getIncomingMessage());
    }

}
