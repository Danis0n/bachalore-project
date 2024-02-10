package ru.fp.billingservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class Report {
    private Long id;
    private String transaction;
    private String descriptor;
    private Double commission;
    private String status;
    private Timestamp date;
    private String sender;
    private String receiver;
}
