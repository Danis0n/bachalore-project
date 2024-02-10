package ru.fp.coreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.coreservice.entity.incomingmessage.IncomingMessage;

@Repository
public interface IncomingMessageRepository extends JpaRepository<IncomingMessage, Long > {
}
