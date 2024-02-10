package ru.fp.participantservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.fp.participantservice.entity.Type;
import ru.fp.participantservice.dto.TypeDto;
import ru.fp.participantservice.repository.TypeRepository;
import  ru.fp.participantservice.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.fp.participantservice.mapper.TypeMapper.mapTypeToDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeService
{
    private final TypeRepository repository;

    public void save(TypeDto dto) {
        log.info("Adding new type: name {}, description {}", dto.getName(), dto.getDescription());
        Type type = new Type();
        type.setName(dto.getName());
        type.setDescription(dto.getDescription());
        type = repository.save(type);
        log.info("New type (id: {}, name: {}, description: {}) was added!", type.getId(), type.getName(), type.getDescription());
    }

    public List<TypeDto> findAll() {
        log.info("Searching all types");
        return repository.findAll().stream().map(mapTypeToDto).collect(Collectors.toList());
    }

    public TypeDto findById(Long id) {
        log.info("Searching type {}", id);
        Optional<Type> result = Optional.ofNullable(repository.findById(id).orElseThrow(() -> new NotFoundException("Type not found")));
        return result.map(mapTypeToDto).get();
    }

    public Type findByName(String name)
    {
        log.info("Searching type {}", name);
        return repository.findOneByName(name);
    }

    public void replace(TypeDto dto) {
        log.info("Replacing type {}", dto.getId());
        Optional<Type> result = Optional.ofNullable(repository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Type not found")));
        Type type = result.get();
        type.setName(dto.getName());
        type.setDescription(dto.getDescription());
        repository.save(type);
        log.info("Type {} was replaced!", dto.getId());
    }

    public void update(TypeDto dto) {
        log.info("Editing type {}", dto.getId());
        Optional<Type> result = Optional.ofNullable(repository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Type not found")));
        Type type = result.get();
        if (dto.getName() != null)
            type.setName(dto.getName());
        if (dto.getDescription() != null)
            type.setDescription(dto.getDescription());
        repository.save(type);
        log.info("Type {} was edited", dto.getId());
    }

    public void remove(Long id) {
        log.info("Deleting type {}", id);
        repository.deleteById(id);
        log.info("Type {} was deleted", id);
    }
}
