package jjose.dev.com.patient.service.domain.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "patients")
@Setter
@Getter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private LocalDate birthDate;

    private String gender;

    private String documentNumber;

    private String nationality;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Relação: 1 Patient pode ter vários contactos
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientContact> contacts = new ArrayList<>();

    // Relação: 1 Patient tem 1 estado clínico atual
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClinicalStatus clinicalStatus;

    // Relação: 1 Patient pode ter vários históricos
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientHistory> histories = new ArrayList<>();
}