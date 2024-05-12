package ru.fp.participantservice.mapper;

import org.springframework.stereotype.Component;
import ru.fp.participantservice.dto.ParticipantDto;
import ru.fp.participantservice.dto.ParticipantInfoDto;
import ru.fp.participantservice.entity.participant.Participant;
import ru.fp.participantservice.entity.participant.ParticipantCredentials;

import java.util.function.Function;

import static ru.fp.participantservice.mapper.TypeMapper.mapTypeToDto;

@Component
public class ParticipantMapper {

    public static Function<ParticipantCredentials, ParticipantDto> mapParticipantToDto =
            participant -> ParticipantDto.builder()
                    .name(participant.getParticipant().getName())
                    .bic(participant.getParticipant().getBic())
                    .role(participant.getParticipant().getRole().getName())
                    .typeName(participant.getParticipant().getType().getName())
                    .login(participant.getLogin())
                    .build();

    public static Function<Participant, ParticipantInfoDto> mapParticipantToInfoDto =
            participant -> ParticipantInfoDto.builder()
                    .id(participant.getId())
                    .name(participant.getName())
                    .bic(participant.getBic())
                    .role(participant.getRole().getName())
                    .type(mapTypeToDto.apply(participant.getType()))
                    .registrationDate(participant.getRegistrationDate())
                    .build();
}
