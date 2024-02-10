package ru.fp.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fp.coreservice.entity.TransferOutbox;

import java.util.List;


@Repository
public interface TransferOutboxRepository extends JpaRepository<TransferOutbox, Long> {

    @Query(value = "SELECT * FROM transfer_outbox t " +
            "ORDER BY t.value_date " +
            "FOR UPDATE SKIP LOCKED LIMIT 10",
            nativeQuery = true
    )
    List<TransferOutbox> findFirst10OrderByValueDateAsc();
}

