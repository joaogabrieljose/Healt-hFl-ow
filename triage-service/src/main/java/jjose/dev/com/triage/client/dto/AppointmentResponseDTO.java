package jjose.dev.com.triage.client.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO(
        Long id,
        Long patientId,
        Long doctorId,
        LocalDate appointmentDate,
        LocalTime startTime,
        LocalTime endTime,
        String reason,
        String notes,
        String status
) {
}