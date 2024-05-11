package ru.fp.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.coreservice.entity.Account;
import ru.fp.coreservice.entity.Currency;
import ru.fp.coreservice.entity.Participant;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCode(String code);
    Optional<Account> findByName(String name);
    Optional<Account> findByCodeAndCurrency(String code, Currency currency);
    List<Account> findAllByParticipantAndCurrencyAndCloseDateIsNullAndIsActiveIsTrue(Participant participant, Currency currency);
    List<Account> findAllByParticipantAndCloseDateIsNullAndIsActiveIsTrue(Participant participant);
}
