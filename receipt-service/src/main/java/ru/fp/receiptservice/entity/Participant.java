package ru.fp.receiptservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bic", unique = true, length = 9, nullable = false)
    private String bic;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
