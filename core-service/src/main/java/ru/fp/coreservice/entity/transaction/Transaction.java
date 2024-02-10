package ru.fp.coreservice.entity.transaction;

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
import ru.fp.coreservice.converter.TransactionStatusConverter;
import ru.fp.coreservice.entity.paydoc.PayDoc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    @Convert(converter = TransactionStatusConverter.class)
    private TransactionStatus status;

    @Column(name = "value_date")
    private Timestamp valueDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "error")
    private String error;

    @ManyToOne
    @JoinColumn(name = "pay_doc_id")
    private PayDoc payDoc;

}