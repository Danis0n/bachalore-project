package ru.fp.participantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.participantservice.entity.participant.ParticipantCredentials;

import java.util.Optional;

@Repository
public interface ParticipantCredentialsRepository extends JpaRepository<ParticipantCredentials, Long> {
    Optional<ParticipantCredentials> findByLogin(String login);
}