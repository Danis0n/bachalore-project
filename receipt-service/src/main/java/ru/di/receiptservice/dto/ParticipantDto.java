package ru.di.receiptservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ParticipantDto implements Serializable {

    private String bic;
    private String typeName;
}
