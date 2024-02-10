package ru.fp.participantservice.mapper;

import org.springframework.stereotype.Component;
import ru.fp.participantservice.dto.TypeDto;
import ru.fp.participantservice.entity.Type;

import java.util.function.Function;

@Component
public class TypeMapper {
    public static Function<Type, TypeDto> mapTypeToDto =
            type -> TypeDto.builder()
                    .id(type.getId())
                    .name(type.getName())
                    .description(type.getDescription())
                    .build();
}
