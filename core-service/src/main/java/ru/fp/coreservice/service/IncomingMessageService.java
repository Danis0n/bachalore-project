package ru.fp.coreservice.service;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import ru.fp.coreservice.client.transaction.TransactionClient;
import ru.fp.coreservice.dto.IncomingMessageDto;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessageStatus;
import ru.fp.coreservice.entity.incomingmessage.Types;
import ru.fp.coreservice.exception.BadRequestException;
import ru.fp.coreservice.exception.NotFoundException;
import ru.fp.coreservice.repository.IncomingMessageRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.fp.coreservice.mapper.IncomingMessageMapper.MAP_CREATE_TO_INCOMING_MESSAGE;
import static ru.fp.coreservice.mapper.IncomingMessageMapper.MAP_INCOMING_MESSAGE_TO_DTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomingMessageService {

    private final IncomingMessageRepository incomingMessageRepository;
    private final ParticipantService participantService;
    private final XSDValidatorService xsdValidatorService;
    private final XMLConverterService xmlConverterService;
    private final TransactionClient transactionClient;
    private final TypeService typeService;
    private final Tracer tracer;

    public List<IncomingMessageDto> getAllIncomingMessages() {
        return incomingMessageRepository.findAll().stream()
                .map(MAP_INCOMING_MESSAGE_TO_DTO)
                .toList();
    }

    public void receivePaymentRequest(IncomingMessageDto dto) {
        Span span = tracer.buildSpan("incoming-message-create").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("Message from bic {} was received", dto.getSender());

            Types pacsType = typeService.findByNameOrThrow("pacs008");

            IncomingMessage incomingMessage = MAP_CREATE_TO_INCOMING_MESSAGE.apply(dto);
            incomingMessage.setType(pacsType);
            incomingMessage.setText(dto.getMsg());

            try {
                xsdValidatorService.validateByXML(dto.getMsg());
            } catch (SAXException | IOException e) {
                setReject(incomingMessage);

                log.info("Message from bic {} wasn't validated due XSD!", dto.getSender());
                throw new RuntimeException(e);
            }

            Optional<Participant> participant = participantService
                    .findParticipantByBic(dto.getSender());

            if (participant.isEmpty()) {
                setReject(incomingMessage);

                log.info("Message from bic {} wasn't saved due participant find error!", dto.getSender());
                throw new NotFoundException("Participant was not found by bic: " + dto.getSender());
            }

            incomingMessage.setSenderParticipant(participant.get());

            log.info("Message from bic {} was saved", dto.getSender());

            Pacs008Dto pacs008 = null;
            try {
                pacs008 = xmlConverterService.buildPACS008FromXML(dto.getMsg());
            } catch (JAXBException | IOException e) {
                incomingMessage.setStatus(IncomingMessageStatus.REJECT);
                incomingMessageRepository.save(incomingMessage);

                throw new BadRequestException(e.getMessage());
            }

            incomingMessage.setValueDate(Timestamp.valueOf(LocalDateTime.now()));
            incomingMessage.setReference(pacs008.getInstrId());
            incomingMessage = incomingMessageRepository.save(incomingMessage);
            transactionClient.proceedTransaction(pacs008, incomingMessage);
        }
        catch(Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        } finally {
            span.finish();
        }
    }

    private void setReject(IncomingMessage incomingMessage) {
        incomingMessage.setSenderParticipant(null);
        incomingMessage.setStatus(IncomingMessageStatus.REJECT);
        incomingMessageRepository.save(incomingMessage);
    }

}
