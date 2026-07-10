package main.java.jjose.dev.com.doctor.dto.doctorScheduleDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record DoctorScheduleDTO(
        Long id,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        Boolean available
) {
}