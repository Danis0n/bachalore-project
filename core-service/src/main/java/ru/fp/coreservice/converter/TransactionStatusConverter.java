package ru.fp.coreservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.fp.coreservice.entity.transaction.TransactionStatus;

@Converter
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(TransactionStatus transactionStatus) {
        return transactionStatus.getName();
    }

    @Override
    public TransactionStatus convertToEntityAttribute(String name) {
        for (TransactionStatus s : TransactionStatus.values()) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
}
