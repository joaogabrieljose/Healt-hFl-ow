package main.java.jjose.dev.com.doctor.dto;

import java.time.DayOfWeek;
import java.util.List;

import jjose.dev.com.doctor.dto.doctorScheduleDTO.DoctorScheduleDTO;

public interface DoctorScheduleService {

    DoctorScheduleDTO createSchedule(Long doctorId, DoctorScheduleDTO dto);

    List<DoctorScheduleDTO> getSchedulesByDoctorId(Long doctorId);

    List<DoctorScheduleDTO> getAvailableSchedulesByDoctorId(Long doctorId);

    List<DoctorScheduleDTO> getSchedulesByDoctorAndDay(Long doctorId, DayOfWeek dayOfWeek);

    DoctorScheduleDTO updateSchedule(Long id, DoctorScheduleDTO dto);

    void deleteSchedule(Long id);
}