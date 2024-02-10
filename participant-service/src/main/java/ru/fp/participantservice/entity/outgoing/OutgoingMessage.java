package ru.fp.participantservice.entity.outgoing;

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
import ru.fp.participantservice.entity.participant.Participant;

import java.sql.Timestamp;

@Entity(name = "outgoing_messages")
@Table(name = "outgoing_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant sender;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OutgoingMessageType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OutgoingMessageStatus status;

    @Column(name = "text")
    private String text;

    @Column(name = "creation_date")
    private Timestamp creationDate;
}
