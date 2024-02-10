package ru.fp.participantservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fp.participantservice.entity.participant.Participant;

@Entity(name = "addresses")
@Table(name = "addresses")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @Column(name = "street", length = 50, nullable = false)
    private String street;

    @Column(name = "house_code", length = 50, nullable = false)
    private String houseCode;

    @Column(name = "postal_code", length = 50, nullable = false)
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
