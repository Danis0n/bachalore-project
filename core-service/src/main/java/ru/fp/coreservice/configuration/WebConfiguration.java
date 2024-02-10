package ru.fp.coreservice.configuration;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xml.sax.SAXException;
import ru.fp.coreservice.util.XSDPACS00800111Validator;
import ru.fp.coreservice.xjc.Document;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public XSDPACS00800111Validator xsdpacs00800111Validator() {
        try {
            return new XSDPACS00800111Validator();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public JAXBContext jaxbContext() {
        try {
            return JAXBContext.newInstance(Document.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

}
