package ru.fp.participantservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fp.participantservice.client.ParticipantClient;
import ru.fp.participantservice.dto.ParticipantDto;
import ru.fp.participantservice.dto.ParticipantInfoDto;
import ru.fp.participantservice.entity.Type;
import ru.fp.participantservice.entity.participant.Participant;
import ru.fp.participantservice.entity.participant.ParticipantCredentials;
import ru.fp.participantservice.exception.NotFoundException;
import ru.fp.participantservice.mapper.ParticipantMapper;
import ru.fp.participantservice.repository.ParticipantCredentialsRepository;
import ru.fp.participantservice.repository.ParticipantRepository;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantService implements UserDetailsService {

    private final ParticipantCredentialsRepository participantCredentialsRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantClient participantClient;
    private final TypeService typeService;

    /**
     * Создание партисипанта.
     * <p>
     * Сохранение партисипанта в БД и отправление запросов на создание
     * в других сервисах.
     * При возникновении ошибки в других сервисах, необходимо удалить
     * данные как из данного сервиса, так и из других.
     */
    @Transactional
    public Participant save(ParticipantDto participantDto) {
        log.info("Creating new Participant {}", participantDto.getBic());

        // При регистрации в форме не указывается тип по умолчанию и имя участника
        final String typeName = Optional.ofNullable(participantDto.getTypeName())
                .orElse(DEFAULT_PARTICIPANT_TYPE_NAME);
        final String participantName = Optional.ofNullable(participantDto.getName())
                .orElse(participantDto.getLogin());

        final String generatedBic = generateBic();

        participantDto.setTypeName(typeName);
        participantDto.setName(participantName);
        participantDto.setBic(generatedBic);

        Type type = typeService.findByName(typeName);

        Participant participant = new Participant();
        participant.setEmail(participantDto.getEmail());
        participant.setName(participantName);
        participant.setBic(generatedBic);
        participant.setType(type);
        participant.setRegistrationDate(new Timestamp(System.currentTimeMillis()));

        val newParticipant = participantRepository.save(participant);
        log.info("Participant was saved under id {}", newParticipant.getId());

        try {
            participantClient.sendParticipantRequest(participantDto);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("Participant with ID {} will be deleted from Participant Service", newParticipant.getId());
            //TODO:  запрос в Core сервис для удаления партисипанта

            throw new RuntimeException(e);
        }

        log.info("End of creating new Participant {}", participantDto.getBic());
        return participant;
    }

    public Participant findByBicOrThrow(String bic) {
        return participantRepository
                .findByBic(bic)
                .orElseThrow(() -> new NotFoundException("User with bic: " + bic + " was not found"));
    }

    public List<ParticipantInfoDto> findAll() {
        log.info("Searching all participants");
        return participantRepository.findAll()
                .stream()
                .map(ParticipantMapper.mapParticipantToInfoDto)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<ParticipantCredentials> credentials = findByLogin(login);

        if (credentials.isEmpty()) {
            throw new UsernameNotFoundException("Wrong login!");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        ParticipantCredentials participantCredentials = credentials.get();

        return new User(
                participantCredentials.getLogin(),
                participantCredentials.getHashedPassword(),
                authorities
        );
    }

    public Optional<ParticipantCredentials> findByLogin(String login) {
        return participantCredentialsRepository.findByLogin(login);
    }

    public void saveCredentials(ParticipantCredentials credentials) {
        participantCredentialsRepository.save(credentials);
    }

    private static final String BIC_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DEFAULT_PARTICIPANT_TYPE_NAME = "pacs008";

    private String generateBic() {
        final StringBuilder bic = new StringBuilder();
        final Random random = ThreadLocalRandom.current();

        for (int i = 0; i < 6; i++) {
            bic.append(BIC_ALPHABET.charAt(random.nextInt(BIC_ALPHABET.length())));
        }

        bic.append(random.nextInt(10, 99));

        return bic.toString();
    }
}