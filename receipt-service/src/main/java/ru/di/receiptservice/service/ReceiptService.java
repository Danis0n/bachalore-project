package ru.di.receiptservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.di.receiptservice.dto.ReceiptDto;
import ru.di.receiptservice.entity.Receipt;
import ru.di.receiptservice.repository.ReceiptRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public List<Receipt> findFirst10OrderByValueDateAsc() {
        return receiptRepository.findFirst10OrderByValueDateAsc();
    }

    public void save(Receipt receipt) {
        receiptRepository.save(receipt);
    }
}
