package ru.fp.billingservice.entity.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fp.billingservice.entity.Currency;
import ru.fp.billingservice.entity.Participant;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;


@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "sender_participant")
    private Participant senderParticipant;

    @ManyToOne
    @JoinColumn(name = "receiver_participant")
    private Participant receiverParticipant;

    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private Timestamp transactionDate;
}
