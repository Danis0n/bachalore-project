package ru.di.receiptservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.di.receiptservice.entity.Receipt;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query(value = "SELECT * FROM receipt t " +
            "WHERE t.status = 'NEW' "+
            "ORDER BY t.value_date " +
            "FOR UPDATE SKIP LOCKED LIMIT 10",
            nativeQuery = true
    )
    List<Receipt> findFirst10OrderByValueDateAsc();
}
