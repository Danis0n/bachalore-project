package ru.fp.coreservice.util;

import lombok.Getter;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;

@Getter
public class XSDPACS00800111Validator {

    private final Validator validator;
    private final String FILENAME = "/pacs.008.001.11.xsd";

    public XSDPACS00800111Validator() throws SAXException {

        try(InputStream inputStream = getClass().getResourceAsStream(FILENAME)) {
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source source = new StreamSource(inputStream);
            Schema schema = schemaFactory.newSchema(source);

            validator = schema.newValidator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
