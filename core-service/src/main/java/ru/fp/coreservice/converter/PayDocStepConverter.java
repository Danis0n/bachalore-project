package ru.fp.coreservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.fp.coreservice.entity.paydoc.PayDocsStep;

@Converter
public class PayDocStepConverter implements AttributeConverter<PayDocsStep, String> {

    @Override
    public String convertToDatabaseColumn(PayDocsStep payDocsStep) {
        return payDocsStep.getName();
    }

    @Override
    public PayDocsStep convertToEntityAttribute(String name) {
        for (PayDocsStep s : PayDocsStep.values()) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
}
