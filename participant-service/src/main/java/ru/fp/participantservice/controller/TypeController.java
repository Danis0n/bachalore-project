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
import ru.fp.participantservice.dto.TypeDto;
import ru.fp.participantservice.service.TypeService;

import java.util.List;

import static ru.fp.participantservice.mapper.TypeMapper.mapTypeToDto;

@RestController
@RequestMapping("api/types")
@RequiredArgsConstructor
public class TypeController
{
    private final TypeService typeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody TypeDto dto)
    {
        typeService.save(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TypeDto> findAll()
    {
        return typeService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TypeDto findById(@PathVariable("id") Long id)
    {
        return typeService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void replace(@RequestBody TypeDto dto)
    {
        typeService.replace(dto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void set(@RequestBody TypeDto dto)
    {
        typeService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("id") Long id) {
        typeService.remove(id);
    }
}