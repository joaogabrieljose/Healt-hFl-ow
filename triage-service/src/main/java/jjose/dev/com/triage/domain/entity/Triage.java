package jjose.dev.com.triage.domain.entity;

import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "triages")
@Data
public class Triage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID do paciente vindo do patient-service
    private Long patientId;

    // ID da consulta vindo do scheduling-service
    private Long appointmentId;

    private String mainComplaint;

    private String symptoms;

    private String urgencyLevel;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Relação: uma triagem tem um conjunto de sinais vitais
    @OneToOne(mappedBy = "triage", cascade = CascadeType.ALL, orphanRemoval = true)
    private TriageVitalSigns vitalSigns;
}