package jjose.dev.com.patient.service.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clinical_status")
@Getter
@Setter
public class ClinicalStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String bloodType;

    private String allergies;

    private String chronicDiseases;

    private String currentMedications;

    private String clinicalNotes;

    private String status;

    private LocalDateTime updatedAt;

    // Relação: 1 estado clínico pertence a 1 paciente
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", unique = true)
    private Patient patient;
}