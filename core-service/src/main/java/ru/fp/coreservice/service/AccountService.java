package ru.fp.coreservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.coreservice.entity.Account;
import ru.fp.coreservice.entity.Currency;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.exception.NotFoundException;
import ru.fp.coreservice.repository.AccountRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public boolean isVerified(Account account) {
        return account != null &&
                account.getIsActive() &&
                account.getCloseDate() == null;
    }

    public Account findAccountByCodeAndCurrencyOrThrow(String accountCode, Currency currency) {
        return accountRepository.findByCodeAndCurrency(accountCode, currency)
                .orElseThrow(() -> new NotFoundException
                        ("Account with current code and currency was not found!")
                );
    }

    public Account findAccountByCodeAndCurrencyOrNull(String accountCode, Currency currency) {
        return accountRepository.findByCodeAndCurrency(accountCode, currency).orElse(null);
    }

    public Account findAccountByBicAndCurrency(Participant participant, Currency currency) {
        return accountRepository.findAllByParticipantAndCurrency(participant, currency).get(0);
    }
}
