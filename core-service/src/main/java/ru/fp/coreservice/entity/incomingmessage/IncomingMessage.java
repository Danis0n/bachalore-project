package ru.fp.coreservice.entity.incomingmessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fp.coreservice.entity.Participant;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "incoming_messages")
@Table(name = "incoming_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant senderParticipant;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Types type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private IncomingMessageStatus status;

    @Column(name = "text")
    private String text;

    @Column(name = "reference", length = 50, unique = true, nullable = false)
    private String reference;

    @Column(name = "value_date")
    private Timestamp valueDate;

    @Column(name = "creation_date")
    private Timestamp creationDate;
}
