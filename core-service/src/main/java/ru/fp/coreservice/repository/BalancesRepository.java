package ru.fp.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fp.coreservice.entity.Account;
import ru.fp.coreservice.entity.Balances;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalancesRepository extends JpaRepository<Balances, Long> {
    Optional<Balances> findByAccount(Account account);

    @Query("SELECT b FROM Balances b WHERE b.account.participant.bic = ?1 AND b.account.isActive = true")
    List<Balances> findBalancesByParticipantBic(String bic);
}