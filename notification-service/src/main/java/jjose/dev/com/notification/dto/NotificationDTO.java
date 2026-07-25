package jjose.dev.com.notification.dto;

import java.time.LocalDateTime;

public record NotificationDTO(
        Long id,
        String type,
        String channel,
        String recipient,
        String subject,
        String message,
        String status,
        Long appointmentId,
        Long patientId,
        Long doctorId,
        LocalDateTime createdAt
) {
}