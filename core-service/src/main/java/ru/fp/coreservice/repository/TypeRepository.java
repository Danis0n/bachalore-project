package ru.fp.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.coreservice.entity.incomingmessage.Types;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Types, Long> {
    Optional<Types> findByCode(String code);
}
