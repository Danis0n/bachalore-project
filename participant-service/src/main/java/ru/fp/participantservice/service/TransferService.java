package ru.fp.participantservice.service;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.participantservice.client.ParticipantClient;
import ru.fp.participantservice.dto.OutgoingMessageDto;
import ru.fp.participantservice.dto.TransferDto;
import ru.fp.participantservice.entity.outgoing.OutgoingMessage;
import ru.fp.participantservice.entity.outgoing.OutgoingMessageStatus;
import ru.fp.participantservice.entity.outgoing.OutgoingMessageType;
import ru.fp.participantservice.entity.participant.Participant;
import ru.fp.participantservice.exception.NotFoundException;
import ru.fp.participantservice.repository.OutgoingMessageRepository;
import ru.fp.participantservice.xjc.AccountIdentification4Choice;
import ru.fp.participantservice.xjc.ActiveCurrencyAndAmount;
import ru.fp.participantservice.xjc.BranchAndFinancialInstitutionIdentification6;
import ru.fp.participantservice.xjc.CashAccount40;
import ru.fp.participantservice.xjc.ChargeBearerType1Code;
import ru.fp.participantservice.xjc.CreditTransferTransaction58;
import ru.fp.participantservice.xjc.Document;
import ru.fp.participantservice.xjc.FIToFICustomerCreditTransferV11;
import ru.fp.participantservice.xjc.FinancialInstitutionIdentification18;
import ru.fp.participantservice.xjc.GenericAccountIdentification1;
import ru.fp.participantservice.xjc.GroupHeader96;
import ru.fp.participantservice.xjc.ObjectFactory;
import ru.fp.participantservice.xjc.PartyIdentification135;
import ru.fp.participantservice.xjc.PaymentIdentification13;
import ru.fp.participantservice.xjc.SettlementInstruction11;
import ru.fp.participantservice.xjc.SettlementMethod1Code;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Random;

import static java.util.UUID.randomUUID;
import static ru.fp.participantservice.mapper.OutgoingMessageMapper.MAP_OUTGOING_MESSAGE_TO_DTO;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransferService {

    private final OutgoingMessageRepository outgoingMessageRepository;
    private final ParticipantService participantService;
    private final CurrencyService currencyService;
    private final ParticipantClient participantClient;
    private final Tracer tracer;

    private final String DEFAULT_CURRENCY = "RUB";

    @Transactional
    public void createTransferRequest(TransferDto dto) {
        Span span = tracer.buildSpan("create-transfer-request").start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("Transfer from {} to {} {} " + DEFAULT_CURRENCY, dto.getBicCd(), dto.getBicDb(), dto.getValue());
            val curTime = new Timestamp(System.currentTimeMillis());
            Document document = generatePacs008(curTime, dto);
            log.info("pacs.008 was generated");

            Participant participant = participantService.findByBicOrThrow(dto.getBicDb());
            OutgoingMessage outgoingMessage = new OutgoingMessage();
            outgoingMessage.setSender(participant);
            outgoingMessage.setType(OutgoingMessageType.PACS008);
            outgoingMessage.setStatus(OutgoingMessageStatus.NEW);
            outgoingMessage.setCreationDate(curTime);
            outgoingMessage.setText(convertDocumentToString(document));
            outgoingMessage = outgoingMessageRepository.save(outgoingMessage);
            log.info("Outgoing message was created with id: {}", outgoingMessage.getId());

            val outMessageDto = MAP_OUTGOING_MESSAGE_TO_DTO.apply(outgoingMessage);

            try {
                participantClient.sendOutgoingMessageRequest(outMessageDto);
                outgoingMessage.setStatus(OutgoingMessageStatus.SENT);
                log.info("Outgoing message with id {} was sent", outgoingMessage.getId());

            } catch (Exception e) {
                log.info(e.getMessage());
                outgoingMessage.setStatus(OutgoingMessageStatus.REJECT);
            }
        }
        catch (Exception e) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e, Fields.MESSAGE, e.getMessage()));
            throw e;
        }
        finally {
            span.finish();
        }

    }

    private String convertDocumentToString(Document document) {
        try {
            JAXBContext context = JAXBContext.newInstance(Document.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            ObjectFactory objectFactory = new ObjectFactory();
            val jaxbDocument = objectFactory.createDocument(document);
            mar.marshal(jaxbDocument, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Расшифровка полей происходила на основе:
     * <p>
     * <a href="https://developer.tkey7.com/ru/docs-iso-20022/pacs-008-fi-to-fi-customer-credit-transfer/">
     *     Портал разработчика облачного платёжного решения TKEY7</a>
     * <p>
     * <a href="https://developers.sber.ru/docs/ru/sberconnect/sc-api/payments/init/post">
     *     sber: Сервис передачи распоряжения на перевод средств в валюте РФ</a>
     * <p>
     * <a href="https://v8.1c.ru/tekhnologii/obmen-dannymi-i-integratsiya/standarty-i-formaty/standart-vzaimodeystviya-po-tekhnologii-directbank/opisanie-standarta-vzaimodeystviya-mezhdu-1s-predpriyatie-8-i-bankovskim-servisom/prikladnoy-uroven-vzaimodeystviya/">
     *     1С:Предприятие 8. Описание формата документа Поручение на перевод валюты (ISO 20022)</a>
     */
    private Document generatePacs008(Timestamp curTime, TransferDto dto) {
        Document document = new Document();
        FIToFICustomerCreditTransferV11 fiToFICustomerCreditTransferV11 = new FIToFICustomerCreditTransferV11();

        // Секция: Реквизиты сообщения
        val currency = currencyService.findCurrencyByCodeOrThrow(DEFAULT_CURRENCY);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        val grpHdr = new GroupHeader96();
        // Идентификатор сообщения
        // YYYYMMDD + CcyCode + RandomNumber + BICFI
        grpHdr.setMsgId(formatter.format(curTime) + currency.getCode() + generateStringOfNumbers(10) + dto.getBicDb());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(curTime);
        XMLGregorianCalendar xCal;
        try {
            xCal = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        // Дата и время создания сообщения
        grpHdr.setCreDtTm(xCal);

        // Количество распоряжений в группе распоряжений
        grpHdr.setNbOfTxs("1");

        // Сумма перевода
        grpHdr.setCtrlSum(dto.getValue());

        val settlementInstruction11 = new SettlementInstruction11();
        settlementInstruction11.setSttlmMtd(SettlementMethod1Code.INGA);


        /* Внутренний идентификатор Способа осуществления расчётов, незарегистрированный в ISO.
        Обязательное поле, нам вроде подходит TDSA, но данный идентификатор отсутствует в xsd
         */
        grpHdr.setSttlmInf(settlementInstruction11);



        fiToFICustomerCreditTransferV11.setGrpHdr(grpHdr);

        // Секция: Реквизиты по кредиту (зачисления)
        val creditTransferTransaction58 = new CreditTransferTransaction58();

        // Секция: идентификаторы платежа
        val paymentIdentification13 = new PaymentIdentification13();
        // CcyCode + YYYYMMDD + BICFI + Prefix + RandomNumber
        paymentIdentification13.setInstrId(currency.getCode() + formatter.format(curTime) + dto.getBicDb()
                + "B" + generateStringOfNumbers(9));
        // Уникальный сквозной идентификатор
        paymentIdentification13.setEndToEndId(randomUUID().toString().substring(1));
        creditTransferTransaction58.setPmtId(paymentIdentification13);

        val activeCurrencyAndAmount = new ActiveCurrencyAndAmount();
        activeCurrencyAndAmount.setCcy(currency.getCode());
        activeCurrencyAndAmount.setValue(dto.getValue());

        // Идентификатор расчётного Актива в TKEY7 и сумма межбанковского перевода
        creditTransferTransaction58.setIntrBkSttlmAmt(activeCurrencyAndAmount);

        // Все расходы по данной транзакции относятся на счёт Плательщика
        creditTransferTransaction58.setChrgBr(ChargeBearerType1Code.DEBT);

        // Секция: Плательщик
        val partyIdentification135 = new PartyIdentification135();
        creditTransferTransaction58.setDbtr(partyIdentification135);

        // Секция: Получатель
        val partyIdentification135_ct = new PartyIdentification135();
        creditTransferTransaction58.setCdtr(partyIdentification135_ct);

        // Секция: Банк получателя
        val financialInstitutionIdentification18 = new FinancialInstitutionIdentification18();
        financialInstitutionIdentification18.setBICFI(dto.getBicCd());
        val branchAndFinancialInstitutionIdentification6 = new BranchAndFinancialInstitutionIdentification6();
        branchAndFinancialInstitutionIdentification6.setFinInstnId(financialInstitutionIdentification18);
        creditTransferTransaction58.setCdtrAgt(branchAndFinancialInstitutionIdentification6);

        if (dto.getCodeCd() != null) {
            CashAccount40 cashAccount40 = new CashAccount40();
            AccountIdentification4Choice accountIdentification4Choice = new AccountIdentification4Choice();
            GenericAccountIdentification1 genericAccountIdentification1 = new GenericAccountIdentification1();
            genericAccountIdentification1.setId(dto.getCodeCd());
            accountIdentification4Choice.setOthr(genericAccountIdentification1);
            cashAccount40.setId(accountIdentification4Choice);
            creditTransferTransaction58.setCdtrAgtAcct(cashAccount40);
        }

        // Секция: Банк плательщика
        val financialInstitutionIdentification18_cred = new FinancialInstitutionIdentification18();
        financialInstitutionIdentification18_cred.setBICFI(dto.getBicDb());
        val branchAndFinancialInstitutionIdentification6_cred = new BranchAndFinancialInstitutionIdentification6();
        branchAndFinancialInstitutionIdentification6_cred.setFinInstnId(financialInstitutionIdentification18_cred);
        creditTransferTransaction58.setDbtrAgt(branchAndFinancialInstitutionIdentification6_cred);

        if (dto.getCodeDb() != null) {
            CashAccount40 cashAccount40 = new CashAccount40();
            AccountIdentification4Choice accountIdentification4Choice = new AccountIdentification4Choice();
            GenericAccountIdentification1 genericAccountIdentification1 = new GenericAccountIdentification1();
            genericAccountIdentification1.setId(dto.getCodeDb());
            accountIdentification4Choice.setOthr(genericAccountIdentification1);
            cashAccount40.setId(accountIdentification4Choice);
            creditTransferTransaction58.setDbtrAgtAcct(cashAccount40);
        }

        fiToFICustomerCreditTransferV11.getCdtTrfTxInf().add(creditTransferTransaction58);
        document.setFIToFICstmrCdtTrf(fiToFICustomerCreditTransferV11);
        return document;
    }

    private String generateStringOfNumbers(int length) {
        Random random = new Random();
        StringBuilder number = new StringBuilder("0");

        for (int i = 0; i < length - 1; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }

    public OutgoingMessageDto findByIdOrThrow(Long id) {
        return outgoingMessageRepository
                .findById(id)
                .map(MAP_OUTGOING_MESSAGE_TO_DTO)
                .orElseThrow(() -> new NotFoundException("message was not found for id: " + id));
    }
}
