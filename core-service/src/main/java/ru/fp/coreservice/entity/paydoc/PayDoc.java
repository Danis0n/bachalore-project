package ru.fp.coreservice.entity.paydoc;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fp.coreservice.converter.PayDocStepConverter;
import ru.fp.coreservice.entity.Currency;
import ru.fp.coreservice.entity.Participant;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.UUID;


@Entity
@Table(name = "pay_docs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayDoc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "guid")
    private UUID guid;

    @Column(name = "debit_acc", length = 20)
    private String debitAcc;

    @Column(name = "credit_acc", length = 20)
    private String creditAcc;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "step")
    @Convert(converter = PayDocStepConverter.class)
    private PayDocsStep step;

    @Column(name = "value_date")
    private Timestamp valueDate;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private IncomingMessage message;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "finish_time")
    private Timestamp finishTime;

    @Column(name = "stime_processed")
    private Duration stimeProcessed;

}
