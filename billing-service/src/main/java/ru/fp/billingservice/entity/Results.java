package ru.fp.billingservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fp.billingservice.entity.decriptor.Descriptor;
import ru.fp.billingservice.entity.transaction.Transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Results {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "descriptor_id")
    private Descriptor descriptor;

    @Column(name = "commission_amount")
    private BigDecimal commissionAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CalculationStatus status;

    @Column(name = "date")
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "sender_participant")
    private Participant senderParticipant;

    @ManyToOne
    @JoinColumn(name = "receiver_participant")
    private Participant receiverParticipant;
}
