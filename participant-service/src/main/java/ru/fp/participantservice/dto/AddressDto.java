package ru.fp.participantservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressDto {
    private Long id;
    private String city;
    private String street;
    private String houseCode;
    private String postalCode;
    private CountryDto country;
}
