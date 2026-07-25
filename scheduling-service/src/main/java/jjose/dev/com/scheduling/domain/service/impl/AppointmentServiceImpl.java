package jjose.dev.com.scheduling.domain.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import feign.FeignException;
import jjose.dev.com.scheduling.client.DoctorClient;
import jjose.dev.com.scheduling.client.PatientClient;
import jjose.dev.com.scheduling.client.dto.DoctorResponseDTO;
import jjose.dev.com.scheduling.client.dto.PatientResponseDTO;
import jjose.dev.com.scheduling.domain.entity.Appointment;
import jjose.dev.com.scheduling.domain.entity.AppointmentStatusHistory;
import jjose.dev.com.scheduling.domain.enums.AppointmentStatus;
import jjose.dev.com.scheduling.domain.repository.AppointmentRepository;
import jjose.dev.com.scheduling.domain.repository.AppointmentStatusHistoryRepository;
import jjose.dev.com.scheduling.dto.appointmentDTO.AppointmentDTO;
import jjose.dev.com.scheduling.events.AppointmentCreatedEvent;
import jjose.dev.com.scheduling.events.AppointmentStatusChangedEvent;
import jjose.dev.com.scheduling.messaging.AppointmentEventPublisher;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentStatusHistoryRepository statusHistoryRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;
    private final AppointmentEventPublisher appointmentEventPublisher;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            AppointmentStatusHistoryRepository statusHistoryRepository,
            PatientClient patientClient,
            DoctorClient doctorClient,
            AppointmentEventPublisher appointmentEventPublisher
    ) {
        this.appointmentRepository = appointmentRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
        this.appointmentEventPublisher = appointmentEventPublisher;
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {

        PatientResponseDTO patient;

        try {
            patient = patientClient.getPatientById(dto.patientId());
        } catch (FeignException.NotFound ex) {
            throw new RuntimeException("Paciente não encontrado com ID: " + dto.patientId());
        } catch (FeignException ex) {
            throw new RuntimeException("Erro ao comunicar com o patient-service.");
        }

        if (patient == null || patient.id() == null) {
            throw new RuntimeException("Paciente não encontrado com ID: " + dto.patientId());
        }

        DoctorResponseDTO doctor;

        try {
            doctor = doctorClient.getDoctorById(dto.doctorId());
        } catch (FeignException.NotFound ex) {
            throw new RuntimeException("Médico não encontrado com ID: " + dto.doctorId());
        } catch (FeignException ex) {
            throw new RuntimeException("Erro ao comunicar com o doctor-service.");
        }

        if (doctor == null || doctor.id() == null) {
            throw new RuntimeException("Médico não encontrado com ID: " + dto.doctorId());
        }

        if (!"ACTIVE".equalsIgnoreCase(doctor.status())) {
            throw new RuntimeException("Não é possível marcar consulta com um médico inativo.");
        }

        boolean existsAppointment = appointmentRepository.existsByDoctorIdAndAppointmentDateAndStartTime(
                dto.doctorId(),
                dto.appointmentDate(),
                dto.startTime()
        );

        if (existsAppointment) {
            throw new RuntimeException("Já existe uma consulta marcada para este médico neste horário.");
        }

        Appointment appointment = toEntity(dto);

        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentStatus.SCHEDULED);
        }

        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        AppointmentStatusHistory history = new AppointmentStatusHistory();
        history.setAppointment(savedAppointment);
        history.setPreviousStatus(null);
        history.setNewStatus(savedAppointment.getStatus());
        history.setReason("Consulta criada.");
        history.setChangedAt(LocalDateTime.now());

        statusHistoryRepository.save(history);

        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
                savedAppointment.getId(),
                savedAppointment.getPatientId(),
                savedAppointment.getDoctorId(),
                savedAppointment.getAppointmentDate(),
                savedAppointment.getStartTime(),
                savedAppointment.getEndTime(),
                savedAppointment.getReason(),
                savedAppointment.getStatus().name(),
                savedAppointment.getCreatedAt()
        );

        appointmentEventPublisher.publishAppointmentCreated(event);

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

        AppointmentStatusHistory history = new AppointmentStatusHistory();
        history.setAppointment(updatedAppointment);
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setReason(reason);
        history.setChangedAt(LocalDateTime.now());

        statusHistoryRepository.save(history);

        AppointmentStatusChangedEvent event = new AppointmentStatusChangedEvent(
                updatedAppointment.getId(),
                updatedAppointment.getPatientId(),
                updatedAppointment.getDoctorId(),
                previousStatus != null ? previousStatus.name() : null,
                newStatus.name(),
                reason,
                LocalDateTime.now()
        );

        appointmentEventPublisher.publishAppointmentStatusChanged(event);

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