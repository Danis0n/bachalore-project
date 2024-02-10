package ru.di.receiptservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.di.receiptservice.dto.ParticipantDto;
import ru.di.receiptservice.entity.Participant;
import ru.di.receiptservice.repository.ParticipantRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public final class ParticipantService {

    private final ParticipantRepository repository;

    public Optional<Participant> findByBic(final String bic) {
        return repository.findByBic(bic);
    }

    public Participant save(final ParticipantDto dto) {
        val bic = dto.getBic();

        log.info("Creating new Participant {}", bic);

        val participant = new Participant();
        participant.setBic(dto.getBic());
        participant.setType(dto.getTypeName());

        val createdParticipant = repository.save(participant);

        log.info("Participant BIC: {}, ID: {} saved", bic, createdParticipant.getId());
        return createdParticipant;
    }

}
