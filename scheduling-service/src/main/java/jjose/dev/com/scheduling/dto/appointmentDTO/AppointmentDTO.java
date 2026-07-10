package jjose.dev.com.scheduling.dto.appointmentDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import jjose.dev.com.scheduling.domain.enums.AppointmentStatus;

public record AppointmentDTO(
        Long id,

        Long patientId,

        Long doctorId,

        LocalDate appointmentDate,

        LocalTime startTime,

        LocalTime endTime,

        String reason,

        String notes,

        AppointmentStatus status
) {
}