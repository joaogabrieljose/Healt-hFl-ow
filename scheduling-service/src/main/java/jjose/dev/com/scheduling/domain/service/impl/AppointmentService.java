package jjose.dev.com.scheduling.domain.service.impl;

import java.time.LocalDate;
import java.util.List;

import jjose.dev.com.scheduling.domain.enums.AppointmentStatus;
import jjose.dev.com.scheduling.dto.appointmentDTO.AppointmentDTO;

public interface AppointmentService {

    AppointmentDTO createAppointment(AppointmentDTO dto);

    List<AppointmentDTO> getAllAppointments();

    AppointmentDTO getAppointmentById(Long id);

    List<AppointmentDTO> getAppointmentsByPatientId(Long patientId);

    List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId);

    List<AppointmentDTO> getAppointmentsByDate(LocalDate appointmentDate);

    List<AppointmentDTO> getAppointmentsByStatus(AppointmentStatus status);

    AppointmentDTO updateAppointment(Long id, AppointmentDTO dto);

    AppointmentDTO updateAppointmentStatus(Long id, AppointmentStatus newStatus, String reason);

    void deleteAppointment(Long id);
}