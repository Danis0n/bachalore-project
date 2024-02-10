package ru.fp.billingservice.entity.inbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "transfer_inbox")
@Table
@Setter
@Getter
@NoArgsConstructor
public class TransferInbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bic_cd")
    private String bicCd;

    @Column(name = "bic_db")
    private String bicDb;

    @Column(name = "value_date")
    private Timestamp valueDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransferInboxStatus status;

}
