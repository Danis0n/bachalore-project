package ru.fp.coreservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.coreservice.dto.AccountBalanceDto;
import ru.fp.coreservice.dto.UpdateBalanceDto;
import ru.fp.coreservice.entity.Account;
import ru.fp.coreservice.entity.Balances;
import ru.fp.coreservice.entity.Currency;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.exception.BadRequestException;
import ru.fp.coreservice.exception.NotFoundException;
import ru.fp.coreservice.repository.BalancesRepository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BalancesService {

    private final BalancesRepository repository;
    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final ParticipantService participantService;

    public void updateBalance(UpdateBalanceDto dto) {
        BigDecimal value = dto.getValue();

        Participant participant = participantService.findParticipantByBicOrThrow(dto.getBic());
        log.debug("Participant was found " + participant.getBic());

        Currency currency = currencyService.findCurrencyByCodeOrThrow(dto.getCurrencyCode());
        log.debug("Currency was found " + currency.getCode());

        Account account = accountService.findAccountByCodeAndCurrencyOrThrow(dto.getAccountCode(), currency);
        log.debug("Account was found " + account.getCode());

        if (!account.getParticipant().equals(participant)) {
            throw new BadRequestException("Participant doesn't have such an account");
        }

        Balances balances = findBalancesByAccountOrThrow(account);
        log.debug("Balances was found " + balances.getId());

        proceedBalanceUpdate(balances, value);
    }

    public void proceedBalanceUpdate(Balances balances, BigDecimal value) {
        BigDecimal current = balances.getAmount();

        BigDecimal finalAmount = current.add(value);

        if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Participant doesn't have enough money to pay");
        }

        balances.setAmount(finalAmount);

        if (value.compareTo(BigDecimal.ZERO) < 0 ) {
            log.debug("updating debit with value: " + value.negate());
            updateCredit(balances, value.negate());
        } else {
            log.debug("updating credit with value: " + value);
            updateDebit(balances, value);
        }

        saveBalances(balances);
    }

    private void updateCredit(Balances balances, BigDecimal value) {
        BigDecimal currentCredit = balances.getCredit();
        balances.setCredit(currentCredit.add(value));
    }

    private void updateDebit(Balances balances, BigDecimal value) {
        BigDecimal currentDebit = balances.getDebit();
        balances.setDebit(currentDebit.add(value));
    }

    public List<AccountBalanceDto> getAccountBalances() {
        return repository.findAll().stream()
                .map(this::mapAccountBalance)
                .toList();
    }

    public List<AccountBalanceDto> getAccountBalancesByBic(String bic) {
        return repository.findBalancesByParticipantBic(bic).stream()
                .map(this::mapAccountBalance)
                .toList();
    }

    private AccountBalanceDto mapAccountBalance(final Balances balances) {
        val account = balances.getAccount();

        return AccountBalanceDto.builder()
                .code(account.getCode())
                .name(account.getName())
                .debitBalance(balances.getDebit())
                .creditBalance(balances.getCredit())
                .currencyName(account.getCurrency().getCode())
                .value(balances.getAmount())
                .build();
    }

    public Balances findBalancesByAccountOrThrow(Account account) {
        return repository.findByAccount(account).orElseThrow(
                () -> new NotFoundException("Balances for current account was not found"));
    }

    public Balances findBalancesByAccountOrNull(Account account) {
        return repository.findByAccount(account).orElse(null);
    }

    public void saveBalances(Balances balances) {
        repository.save(balances);
    }

}
