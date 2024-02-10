package ru.fp.billingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.fp.billingservice.entity.decriptor.DescriptorType;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DescriptorDto implements Serializable {

    private Long id;
    private String name;

    private BigDecimal minPercent;
    private BigDecimal maxPercent;

    private BigDecimal rate;

    private DescriptorType type;
}
