package ru.fp.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.billingservice.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByName(String name);
}
