package ru.fp.coreservice.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.fp.coreservice.dto.Pacs008Dto;
import ru.fp.coreservice.util.XMLHelper;
import ru.fp.coreservice.xjc.CashAccount40;
import ru.fp.coreservice.xjc.CreditTransferTransaction58;
import ru.fp.coreservice.xjc.Document;
import ru.fp.coreservice.xjc.FIToFICustomerCreditTransferV11;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class XMLConverterService {

    private final JAXBContext jaxbContext;

    public Pacs008Dto buildPACS008FromXML(String xml) throws JAXBException, IOException {

        StreamSource xmlSource = XMLHelper.getXmlStreamFromString(xml);
        try (InputStream ignored = xmlSource.getInputStream()) {

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<Document> pacs008Document = unmarshaller.unmarshal(xmlSource, Document.class);

            FIToFICustomerCreditTransferV11 commonData = pacs008Document.getValue().getFIToFICstmrCdtTrf();
            CreditTransferTransaction58 creditTransferData = commonData.getCdtTrfTxInf().get(0);

            CashAccount40 dbtrAgtAcct = creditTransferData.getDbtrAgtAcct();
            CashAccount40 cdtrAgtAcct = creditTransferData.getCdtrAgtAcct();

            String codeDb = null;
            if (dbtrAgtAcct != null) {
                codeDb = dbtrAgtAcct.getId().getOthr().getId();
            }

            String codeCd = null;
            if (creditTransferData.getCdtrAgtAcct() != null) {
                codeCd = cdtrAgtAcct.getId().getOthr().getId();
            }

            String bicDb = creditTransferData.getDbtrAgt().getFinInstnId().getBICFI();
            String bicCd = creditTransferData.getCdtrAgt().getFinInstnId().getBICFI();

            Pacs008Dto pacs008 = Pacs008Dto.builder()
                    .messageId(commonData.getGrpHdr().getMsgId())
                    .bicDb(bicDb)
                    .bicCd(bicCd)
                    .codeDb(codeDb)
                    .codeCd(codeCd)
                    .value(commonData.getGrpHdr().getCtrlSum())
                    .dateTime(convertToLocalDateTime(commonData.getGrpHdr().getCreDtTm()))
                    .endToEndId(creditTransferData.getPmtId().getEndToEndId())
                    .instrId(creditTransferData.getPmtId().getInstrId())
                    .currency(creditTransferData.getIntrBkSttlmAmt().getCcy())
                    .build();

            log.info("Pacs008 was received and converted to dto: " + pacs008.getMessageId());

            return pacs008;
        } catch (JAXBException e) {
            throw new JAXBException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

    }

    private LocalDateTime convertToLocalDateTime(XMLGregorianCalendar calendar) {
        return LocalDateTime.of(
                calendar.getYear(),
                calendar.getMonth(),
                calendar.getDay(),
                calendar.getHour(),
                calendar.getMinute(),
                calendar.getSecond(),
                calendar.getMillisecond()
        );
    }

}
