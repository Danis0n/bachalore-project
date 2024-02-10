package ru.fp.participantservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.participantservice.dto.CurrencyDto;
import ru.fp.participantservice.entity.Currency;
import ru.fp.participantservice.exception.NotFoundException;
import ru.fp.participantservice.repository.CurrencyRepository;

import java.util.List;

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

    public List<CurrencyDto> findAll() {
        return currencyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private CurrencyDto mapToDto(Currency currency) {
        return CurrencyDto.builder()
                .id(currency.getId())
                .code(currency.getCode())
                .description(currency.getDescription())
                .build();
    }

}
