package ru.fp.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fp.billingservice.entity.inbox.TransferInbox;
import ru.fp.billingservice.entity.inbox.TransferInboxStatus;

import java.util.List;

@Repository
public interface TransferInboxRepository extends JpaRepository<TransferInbox, Long> {
    @Query(value = "SELECT * FROM transfer_inbox t " +
            "WHERE t.status = 'NEW' "+
            "ORDER BY t.value_date " +
            "FOR UPDATE SKIP LOCKED LIMIT 10",
            nativeQuery = true
    )
    List<TransferInbox> find10NewTransfers();

    void deleteByStatus(TransferInboxStatus status);
}
