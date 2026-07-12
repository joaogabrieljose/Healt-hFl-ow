package jjose.dev.com.triage.dto;

public record TriageDTO(
        Long id,

        Long patientId,

        Long appointmentId,

        String mainComplaint,

        String symptoms,

        String urgencyLevel,

        String notes
) {
}