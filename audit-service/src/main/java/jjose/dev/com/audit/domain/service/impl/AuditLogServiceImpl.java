package jjose.dev.com.audit.domain.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.audit.domain.entity.AuditLog;
import jjose.dev.com.audit.domain.repository.AuditLogRepository;
import jjose.dev.com.audit.domain.service.AuditLogService;
import jjose.dev.com.audit.dto.AuditLogDTO;
import jjose.dev.com.audit.events.AppointmentCreatedEvent;
import jjose.dev.com.audit.events.AppointmentStatusChangedEvent;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void saveAppointmentCreatedAudit(AppointmentCreatedEvent event) {
        AuditLog auditLog = new AuditLog();

        auditLog.setEventType("APPOINTMENT_CREATED");
        auditLog.setSourceService("scheduling-service");
        auditLog.setEntityType("APPOINTMENT");
        auditLog.setEntityId(event.appointmentId());

        auditLog.setDescription(
                "Consulta criada para o paciente "
                        + event.patientId()
                        + " com o médico "
                        + event.doctorId()
                        + " no dia "
                        + event.appointmentDate()
                        + " das "
                        + event.startTime()
                        + " às "
                        + event.endTime()
                        + ". Motivo: "
                        + event.reason()
        );

        auditLog.setAppointmentId(event.appointmentId());
        auditLog.setPatientId(event.patientId());
        auditLog.setDoctorId(event.doctorId());
        auditLog.setPreviousStatus(null);
        auditLog.setNewStatus(event.status());
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    @Override
    public void saveAppointmentStatusChangedAudit(AppointmentStatusChangedEvent event) {
        AuditLog auditLog = new AuditLog();

        auditLog.setEventType("APPOINTMENT_STATUS_CHANGED");
        auditLog.setSourceService("scheduling-service");
        auditLog.setEntityType("APPOINTMENT");
        auditLog.setEntityId(event.appointmentId());

        auditLog.setDescription(
                "Estado da consulta "
                        + event.appointmentId()
                        + " alterado de "
                        + event.previousStatus()
                        + " para "
                        + event.newStatus()
                        + ". Motivo: "
                        + event.reason()
        );

        auditLog.setAppointmentId(event.appointmentId());
        auditLog.setPatientId(event.patientId());
        auditLog.setDoctorId(event.doctorId());
        auditLog.setPreviousStatus(event.previousStatus());
        auditLog.setNewStatus(event.newStatus());
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    @Override
    public List<AuditLogDTO> getAllAuditLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public AuditLogDTO getAuditLogById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registo de auditoria não encontrado com ID: " + id));

        return toDTO(auditLog);
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByPatientId(Long patientId) {
        return auditLogRepository.findByPatientId(patientId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByAppointmentId(Long appointmentId) {
        return auditLogRepository.findByAppointmentId(appointmentId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByEventType(String eventType) {
        return auditLogRepository.findByEventType(eventType)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private AuditLogDTO toDTO(AuditLog auditLog) {
        return new AuditLogDTO(
                auditLog.getId(),
                auditLog.getEventType(),
                auditLog.getSourceService(),
                auditLog.getEntityType(),
                auditLog.getEntityId(),
                auditLog.getDescription(),
                auditLog.getAppointmentId(),
                auditLog.getPatientId(),
                auditLog.getDoctorId(),
                auditLog.getPreviousStatus(),
                auditLog.getNewStatus(),
                auditLog.getCreatedAt()
        );
    }
}