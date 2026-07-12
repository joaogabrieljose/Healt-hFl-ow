package jjose.dev.com.triage.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TriageVitalSignsDTO(
        Long id,

        Long triageId,

        BigDecimal temperature,

        Integer heartRate,

        Integer respiratoryRate,

        String bloodPressure,

        Integer oxygenSaturation,

        BigDecimal weight,

        BigDecimal height,

        LocalDateTime measuredAt
) {
}