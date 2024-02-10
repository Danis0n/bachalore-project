package ru.fp.coreservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.fp.coreservice.entity.incomingmessage.Types;
import ru.fp.coreservice.exception.NotFoundException;
import ru.fp.coreservice.repository.TypeRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository repository;

    public Types findByNameOrThrow(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Type was not found"));
    }

}
