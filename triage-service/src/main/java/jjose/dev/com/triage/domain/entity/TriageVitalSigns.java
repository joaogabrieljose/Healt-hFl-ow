package jjose.dev.com.triage.domain.entity;

import java.math.BigDecimal;
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
@Table(name = "triage_vital_signs")
@Getter
@Setter
public class TriageVitalSigns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal temperature;

    private Integer heartRate;

    private Integer respiratoryRate;

    private String bloodPressure;

    private Integer oxygenSaturation;

    private BigDecimal weight;

    private BigDecimal height;

    private LocalDateTime measuredAt;

    // Relação: os sinais vitais pertencem a uma triagem
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "triage_id", unique = true)
    private Triage triage;
}