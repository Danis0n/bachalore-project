package ru.fp.participantservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.fp.participantservice.dto.CountryDto;
import ru.fp.participantservice.entity.Country;
import ru.fp.participantservice.entity.Type;
import ru.fp.participantservice.exception.NotFoundException;
import ru.fp.participantservice.repository.CountryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.fp.participantservice.mapper.CountryMapper.mapCountryToDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository repository;

    public void save(CountryDto dto) {
        log.info("Adding new country: name {}, code: {}", dto.getName(), dto.getCode());
        Country country = new Country();
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country = repository.save(country);
        log.info("New country (id: {}, name {}, code: {}) was added!", country.getId(), country.getName(), country.getCode());
    }

    public List<CountryDto> findAll() {
        log.info("Searching all countries");
        return repository.findAll().stream().map(mapCountryToDto).collect(Collectors.toList());
    }

    public CountryDto findById(Long id) {
        log.info("Searching country {}", id);
        Optional<Country> result = Optional.ofNullable(repository.findById(id).orElseThrow(() -> new NotFoundException("Type not found")));
        return result.map(mapCountryToDto).get();
    }

    public void replace(CountryDto dto) {
        log.info("Replacing country {}", dto.getId());
        Optional<Country> result = Optional.ofNullable(repository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Type not found")));
        Country country = result.get();
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country = repository.save(country);
        log.info("Country {} was replaced!", dto.getId());
    }

    public void update(CountryDto dto) {
        log.info("Editing country {}", dto.getId());
        Optional<Country> result = Optional.ofNullable(repository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Type not found")));
        Country country = result.get();
        if (dto.getName() != null)
            country.setName(dto.getName());
        if (dto.getCode() != null)
            country.setCode(dto.getCode());
        country = repository.save(country);
        log.info("Country {} was edited", dto.getId());
    }

    public void remove(Long id) {
        log.info("Deleting country {}", id);
        repository.deleteById(id);
        log.info("Country {} was deleted", id);
    }
}
