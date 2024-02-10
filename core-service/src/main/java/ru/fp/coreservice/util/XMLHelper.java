package ru.fp.coreservice.util;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class XMLHelper {

    public static StreamSource getXmlStreamFromString(String xml) {
        return new StreamSource(
                new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))
        );
    }

}
