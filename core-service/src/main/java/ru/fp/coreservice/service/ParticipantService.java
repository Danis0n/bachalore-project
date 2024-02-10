package ru.fp.coreservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fp.coreservice.dto.ParticipantDto;
import ru.fp.coreservice.entity.Account;
import ru.fp.coreservice.entity.Balances;
import ru.fp.coreservice.entity.Currency;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.exception.NotFoundException;
import ru.fp.coreservice.repository.AccountRepository;
import ru.fp.coreservice.repository.BalancesRepository;
import ru.fp.coreservice.repository.ParticipantRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final AccountRepository accountRepository;
    private final BalancesRepository balancesRepository;
    private final CurrencyService currencyService;

    /**
     * Создание партисипанта.
     * <p>
     * Сохранение партисипанта в БД и создание для него аккаунта и счёта
     * с значениями по умолчанию.
     */
    @Transactional
    public void save(ParticipantDto participantDto) {
        log.info("Creating new Participant {}", participantDto.getBic());
        Participant participant = new Participant();
        participant.setBic(participantDto.getBic());
        participant.setDateCreated(new Timestamp(System.currentTimeMillis()));
        participant.setIsActive(true);

        participant = participantRepository.save(participant);
        log.info("Participant was saved under id {}", participant.getId());

        log.info("Creating Account for Participant with id {}", participant.getId());
        Currency currency = currencyService.findCurrencyByCodeOrThrow("RUB");
        Timestamp date = new Timestamp(System.currentTimeMillis());
        String dateFormated = new SimpleDateFormat("yyyyMMdd").format(date);
        Account account = Account.builder()
                .code(participant.getBic() + currency.getCode() + dateFormated)
                .name(participant.getBic())
                .accType("cash")
                .currency(currency)
                .isActive(true)
                .participant(participant)
                .openDate(date)
                .build();
        account = accountRepository.save(account);
        log.info("Account was saved under id {}", account.getId());

        log.info("Creating Balance for Account with id {}", account.getId());
        Balances balance = Balances.builder()
                .account(account)
                .credit(BigDecimal.valueOf(0))
                .debit(BigDecimal.valueOf(0))
                .docDebits(0L)
                .docCredits(0L)
                .amount(BigDecimal.valueOf(0))
                .build();

        balance = balancesRepository.save(balance);
        log.info("Balance was saved under id {}", balance.getId());
    }

    public Participant findParticipantByBicOrThrow(String bic) {
        return participantRepository.findByBic(bic)
                .orElseThrow(() -> new NotFoundException("Participant was not found!"));
    }

    public Optional<Participant> findParticipantByBic(String bic) {
        return participantRepository.findByBic(bic);
    }

}
