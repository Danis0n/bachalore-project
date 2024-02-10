package ru.fp.participantservice.entity.participant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "participant_credentials")
@Table(name = "participant_credentials")
public class ParticipantCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column (name = "login", unique = true, nullable = false)
    private String login;

    @Column (name = "hashed_password", length = 9, nullable = false)
    private String hashedPassword;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

}
