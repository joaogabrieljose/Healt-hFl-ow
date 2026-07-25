package jjose.dev.com.notification.events;

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