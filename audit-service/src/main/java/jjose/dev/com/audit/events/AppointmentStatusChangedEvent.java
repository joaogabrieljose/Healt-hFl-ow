package jjose.dev.com.audit.events;

import java.time.LocalDateTime;

public record AppointmentStatusChangedEvent(
        Long appointmentId,
        Long patientId,
        Long doctorId,
        String previousStatus,
        String newStatus,
        String reason,
        LocalDateTime changedAt
) {
}