package ru.fp.billingservice.entity.decriptor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "descriptor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Descriptor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "date_activate")
    private Timestamp dateActivate;

    @Column(name = "amount_before")
    private BigDecimal amountBefore;

    @Column(name = "amount_after")
    private BigDecimal amountAfter;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DescriptorType type;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "min_percent")
    private BigDecimal minPercent;

    @Column(name = "max_percent")
    private BigDecimal maxPercent;
}
