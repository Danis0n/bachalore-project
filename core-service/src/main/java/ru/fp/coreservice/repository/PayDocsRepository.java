package ru.fp.coreservice.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fp.coreservice.entity.paydoc.PayDoc;

import java.util.List;
import java.util.UUID;

@Repository
public interface PayDocsRepository extends JpaRepository<PayDoc, Long> {
    PayDoc findByGuid(UUID guid);

    @Query(
            value = "select * from pay_docs p where p.credit_acc in :accounts " +
                    "or p.debit_acc in :accounts " +
                    "order by p.value_date desc limit :limit",
            nativeQuery = true
    )
    List<PayDoc> findLatestByAccountsAndLimit(
            @Param("accounts") List<String> accounts,
            @Param("limit") Integer limit
    );
}