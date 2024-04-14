package ru.fp.receiptservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fp.receiptservice.entity.Receipt;
import ru.fp.receiptservice.repository.ReceiptRepository;

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
