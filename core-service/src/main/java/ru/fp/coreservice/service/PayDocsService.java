package ru.fp.coreservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.dto.PayDocsDto;
import ru.fp.coreservice.entity.Currency;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;
import ru.fp.coreservice.entity.paydoc.PayDoc;
import ru.fp.coreservice.entity.paydoc.PayDocsStep;
import ru.fp.coreservice.repository.PayDocsRepository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayDocsService {

    private final PayDocsRepository payDocsRepository;
    private final ParticipantService participantService;
    private final CurrencyService currencyService;

    private final int MOSCOW_TIME = 3;

    public PayDoc create(Pacs008Dto pacs008) {
        Participant participant = participantService
                .findParticipantByBicOrThrow(pacs008.getBicCd());

        Currency currency = currencyService
                .findCurrencyByCodeOrThrow(pacs008.getCurrency());

        PayDoc payDoc = new PayDoc();
        payDoc.setCurrency(currency);
        payDoc.setParticipant(participant);
        payDoc.setAmount(pacs008.getValue());
        payDoc.setValueDate(Timestamp.valueOf(LocalDateTime.now().plusHours(MOSCOW_TIME)));
        payDoc.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        payDoc.setStep(PayDocsStep.CREATE_PAY_DOC);
        return payDocsRepository.save(payDoc);
    }

    @Transactional()
    public PayDoc createPaydocHandler(Pacs008Dto pacs008, IncomingMessage incomingMessage) {

        PayDoc payDoc = create(pacs008);
        incomingMessage.setSenderParticipant(payDoc.getParticipant());
        payDoc.setMessage(incomingMessage);

        payDoc = save(payDoc);
        log.info("Paydoc with id {} step: {}", payDoc.getId(), payDoc.getStep());
        return payDoc;
    }

    public void GuidCreateHandler(PayDoc payDoc) {
        payDoc.setGuid(UUID.randomUUID());
        moveNextStep(payDoc);
    }

    public void moveNextStep(PayDoc payDoc) {
        payDoc.setStep(payDoc.getStep().nextStep());
        save(payDoc);
        log.info("Paydoc with id {} step: {}", payDoc.getId(), payDoc.getStep());
    }

    public PayDoc save(PayDoc payDoc) {
        return payDocsRepository.save(payDoc);
    }

    public void finishPayDoc(PayDoc payDoc) {
        Timestamp finishTime = Timestamp.valueOf(LocalDateTime.now().plusHours(MOSCOW_TIME));
        payDoc.setFinishTime(finishTime);
        Duration duration = Duration
                .between(payDoc.getStartTime().toInstant(), finishTime.toInstant());

        payDoc.setStimeProcessed(duration);
        moveNextStep(payDoc);
    }

    public List<PayDocsDto> getAllDocuments() {
        return payDocsRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    private PayDocsDto map(final PayDoc payDoc) {
        return PayDocsDto.builder()
                .guid(payDoc.getGuid())
                .step(payDoc.getStep())
                .amount(payDoc.getAmount())
                .debitAcc(payDoc.getDebitAcc())
                .creditAcc(payDoc.getCreditAcc())
                .valueDate(payDoc.getValueDate())
                .startTime(payDoc.getStartTime())
                .finishTime(payDoc.getFinishTime())
                .currencyCode(payDoc.getCurrency().getCode())
                .participantBic(payDoc.getParticipant().getBic())
                .build();
    }

    public PayDoc findPaydocByGuid(UUID guid) {
        return payDocsRepository.findByGuid(guid);
    }

    public List<PayDoc> findPaydocsByAccountsAndLimit(List<String> accountCodes, Integer limit) {
        return payDocsRepository.findLatestByAccountsAndLimit(accountCodes, limit);
    }
}
