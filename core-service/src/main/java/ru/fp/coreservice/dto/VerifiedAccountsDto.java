package ru.fp.coreservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class VerifiedAccountsDto implements Serializable {
    private String accountCdCode;
    private String accountDbCode;
    private String error;
}
