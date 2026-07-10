package jjose.dev.com.scheduling.dto.appointmentStatusHistoryDTO;

import java.time.LocalDateTime;

import jjose.dev.com.scheduling.domain.enums.AppointmentStatus;

public record AppointmentStatusHistoryDTO(
        Long id,

        Long appointmentId,

        AppointmentStatus previousStatus,

        AppointmentStatus newStatus,

        String reason,

        LocalDateTime changedAt
) {
}