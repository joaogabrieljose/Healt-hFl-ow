package jjose.dev.com.audit.dto;

import java.time.LocalDateTime;

public record AuditLogDTO(
        Long id,
        String eventType,
        String sourceService,
        String entityType,
        Long entityId,
        String description,
        Long appointmentId,
        Long patientId,
        Long doctorId,
        String previousStatus,
        String newStatus,
        LocalDateTime createdAt
) {
}