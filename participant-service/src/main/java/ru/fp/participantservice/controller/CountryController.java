package ru.fp.participantservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.fp.participantservice.dto.CountryDto;
import ru.fp.participantservice.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CountryDto dto)
    {
        countryService.save(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CountryDto> findAll()
    {
        return countryService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CountryDto findById(@PathVariable("id") Long id)
    {
        return countryService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void replace(@RequestBody CountryDto dto)
    {
        countryService.replace(dto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void set(@RequestBody CountryDto dto) {
        countryService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("id") Long id)
    {
        countryService.remove(id);
    }
}
