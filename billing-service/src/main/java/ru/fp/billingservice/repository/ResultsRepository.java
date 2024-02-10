package ru.fp.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.billingservice.entity.Results;

@Repository
public interface ResultsRepository extends JpaRepository<Results, Long> {
}
