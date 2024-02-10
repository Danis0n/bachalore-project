package ru.fp.participantservice.mapper;

import org.springframework.stereotype.Component;
import ru.fp.participantservice.dto.CountryDto;
import ru.fp.participantservice.entity.Country;

import java.util.function.Function;

@Component
public class CountryMapper {
    public static Function<Country, CountryDto> mapCountryToDto =
            country -> CountryDto.builder()
                    .id(country.getId())
                    .name(country.getName())
                    .code(country.getCode())
                    .build();
}
