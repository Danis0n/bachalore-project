package ru.fp.coreservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import ru.fp.coreservice.util.XMLHelper;
import ru.fp.coreservice.util.XSDPACS00800111Validator;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class XSDValidatorService {

    private final XSDPACS00800111Validator xsdpacs00800111Validator;

    public void validateByXML(String xml) throws SAXException, IOException {

        synchronized (xsdpacs00800111Validator) {

            StreamSource xmlFromString = XMLHelper.getXmlStreamFromString(xml);
            try (InputStream ignored = xmlFromString.getInputStream()) {
                xsdpacs00800111Validator.getValidator().validate(xmlFromString);
            } catch (SAXException e) {
                throw new SAXException("XML validation fail: " + e.getMessage());
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }

        }
    }
}
