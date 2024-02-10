package ru.fp.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.coreservice.entity.paydoc.PayDoc;

import java.util.UUID;

@Repository
public interface PayDocsRepository extends JpaRepository<PayDoc, Long> {
    PayDoc findByGuid(UUID guid);
}