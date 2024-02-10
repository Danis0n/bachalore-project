package ru.fp.participantservice.client;

import io.jaegertracing.internal.JaegerSpanContext;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.fp.participantservice.aop.annotaion.RequestUuid;
import ru.fp.participantservice.dto.OutgoingMessageDto;
import ru.fp.participantservice.dto.ParticipantDto;
import ru.fp.participantservice.mapper.SpanContextMapper;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "broker", name = "profile", havingValue = "rest", matchIfMissing = true)
public class RestClient implements ParticipantClient {

    @Value("${services.core}")
    private String coreUrl;

    @Value("${services.billing}")
    private String billingUrl;

    @Value("${services.receipt}")
    private String receiptUrl;

    private final String PARTICIPANT_CREATE = "participant";
    private final String OUTGOING_MESSAGE_SENT = "incoming-message";
    private final String CORE_SERVICE = "Core";
    private final String BILLING_SERVICE = "Billing";
    private final String RECEIPT_SERVICE = "Receipt";

    private final RestTemplate restTemplate;
    private final Tracer tracer;

    @Override
    public void sendParticipantRequest(@NonNull ParticipantDto participantDto) throws Exception {
        sendParticipantToService(participantDto, coreUrl, CORE_SERVICE);
        sendParticipantToService(participantDto, billingUrl, BILLING_SERVICE);
        sendParticipantToService(participantDto, receiptUrl, BILLING_SERVICE);
    }

    @Override
    @RequestUuid
    public void sendOutgoingMessageRequest(@NonNull OutgoingMessageDto outgoingMessageDto) throws Exception {
        Span span = tracer.buildSpan("send-outgoing-message-request").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            span.setTag("OutgoingMessageUuid", outgoingMessageDto.getUuid());
            outgoingMessageDto.setSpanContext(SpanContextMapper.mapSpanContextToDto.apply((JaegerSpanContext)span.context()));

            HttpEntity<OutgoingMessageDto> request =
                    new HttpEntity<>(outgoingMessageDto);

            ResponseEntity<Void> httpResponse = restTemplate.exchange(
                    coreUrl + OUTGOING_MESSAGE_SENT,
                    HttpMethod.POST,
                    request,
                    Void.class);
            if (httpResponse.getStatusCode().is5xxServerError()) {
                throw new Exception("OutgoingMessage was not sent, due to an Server error");

            } else if (httpResponse.getStatusCode().is4xxClientError()) {
                throw new Exception("OutgoingMessage was not sent, due to an Client error");
            }

        } catch (Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        }
        finally {
            span.finish();
        }
    }

    private void sendRequest(@NonNull final String uri,
                            @NonNull ParticipantDto participantDto
    ) throws Exception {

        HttpEntity<ParticipantDto> request =
                new HttpEntity<>(participantDto);

        ResponseEntity<Boolean> httpResponse = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                Boolean.class);
        if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new Exception("Participant was not saved, due to an Server error");

        } else if (httpResponse.getStatusCode().is4xxClientError()) {
            throw new Exception("Participant was not saved, due to an Client error");
        }

    }

    private void sendParticipantToService(ParticipantDto participantDto, String serviceUrl,
                                          String serviceName) throws Exception {
        log.info("Sending request to {}", serviceName);
        sendRequest(
                serviceUrl + PARTICIPANT_CREATE,
                participantDto
        );
        log.info("Participant with name {} saved in {} service",
                participantDto.getName(), serviceName);
    }
}
