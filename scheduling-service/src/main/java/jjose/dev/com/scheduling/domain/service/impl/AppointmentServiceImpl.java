package jjose.dev.com.scheduling.domain.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.scheduling.domain.entity.Appointment;
import jjose.dev.com.scheduling.domain.entity.AppointmentStatusHistory;
import jjose.dev.com.scheduling.domain.enums.AppointmentStatus;

import jjose.dev.com.scheduling.domain.repository.AppointmentRepository;
import jjose.dev.com.scheduling.domain.repository.AppointmentStatusHistoryRepository;
import jjose.dev.com.scheduling.dto.appointmentDTO.AppointmentDTO;


@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentStatusHistoryRepository statusHistoryRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            AppointmentStatusHistoryRepository statusHistoryRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.statusHistoryRepository = statusHistoryRepository;
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {

        // Verifica se o médico já tem consulta marcada na mesma data e hora
        boolean existsAppointment = appointmentRepository.existsByDoctorIdAndAppointmentDateAndStartTime(
                dto.doctorId(),
                dto.appointmentDate(),
                dto.startTime()
        );

        if (existsAppointment) {
            throw new RuntimeException("Já existe uma consulta marcada para este médico neste horário.");
        }

        Appointment appointment = toEntity(dto);

        // Se o status vier vazio, assume SCHEDULED como padrão
        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentStatus.SCHEDULED);
        }

        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Cria histórico inicial
        AppointmentStatusHistory history = new AppointmentStatusHistory();
        history.setAppointment(savedAppointment);
        history.setPreviousStatus(null);
        history.setNewStatus(savedAppointment.getStatus());
        history.setReason("Consulta criada.");
        history.setChangedAt(LocalDateTime.now());

        statusHistoryRepository.save(history);

        return toDTO(savedAppointment);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id));

        return toDTO(appointment);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDate(LocalDate appointmentDate) {
        return appointmentRepository.findByAppointmentDate(appointmentDate)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id));

        appointment.setPatientId(dto.patientId());
        appointment.setDoctorId(dto.doctorId());
        appointment.setAppointmentDate(dto.appointmentDate());
        appointment.setStartTime(dto.startTime());
        appointment.setEndTime(dto.endTime());
        appointment.setReason(dto.reason());
        appointment.setNotes(dto.notes());

        if (dto.status() != null) {
            appointment.setStatus(dto.status());
        }

        appointment.setUpdatedAt(LocalDateTime.now());

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return toDTO(updatedAppointment);
    }

    @Override
    public AppointmentDTO updateAppointmentStatus(Long id, AppointmentStatus newStatus, String reason) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id));

        AppointmentStatus previousStatus = appointment.getStatus();

        appointment.setStatus(newStatus);
        appointment.setUpdatedAt(LocalDateTime.now());

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Guarda histórico da alteração de estado
        AppointmentStatusHistory history = new AppointmentStatusHistory();
        history.setAppointment(updatedAppointment);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setReason(reason);
        history.setChangedAt(LocalDateTime.now());

        statusHistoryRepository.save(history);

        return toDTO(updatedAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id));

        appointmentRepository.delete(appointment);
    }

    private Appointment toEntity(AppointmentDTO dto) {
        Appointment appointment = new Appointment();

        appointment.setPatientId(dto.patientId());
        appointment.setDoctorId(dto.doctorId());
        appointment.setAppointmentDate(dto.appointmentDate());
        appointment.setStartTime(dto.startTime());
        appointment.setEndTime(dto.endTime());
        appointment.setReason(dto.reason());
        appointment.setNotes(dto.notes());
        appointment.setStatus(dto.status());

        return appointment;
    }

    private AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getReason(),
                appointment.getNotes(),
                appointment.getStatus()
        );
    }
}