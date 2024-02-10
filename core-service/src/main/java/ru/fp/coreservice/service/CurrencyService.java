package ru.fp.coreservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.coreservice.entity.Currency;
import ru.fp.coreservice.exception.NotFoundException;
import ru.fp.coreservice.repository.CurrencyRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public Currency findCurrencyByCodeOrThrow(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException
                        ("Currency: " + code + " was not found!")
                );
    }
}
