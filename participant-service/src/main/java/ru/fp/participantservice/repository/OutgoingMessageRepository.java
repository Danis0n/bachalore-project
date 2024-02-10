package ru.fp.participantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.participantservice.entity.outgoing.OutgoingMessage;

@Repository
public interface OutgoingMessageRepository extends JpaRepository<OutgoingMessage, Long > {
}
