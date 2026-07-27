package jjose.dev.com.audit.domain.service;

import java.util.List;

import jjose.dev.com.audit.dto.AuditLogDTO;
import jjose.dev.com.audit.events.AppointmentCreatedEvent;
import jjose.dev.com.audit.events.AppointmentStatusChangedEvent;

public interface AuditLogService {

    void saveAppointmentCreatedAudit(AppointmentCreatedEvent event);

    void saveAppointmentStatusChangedAudit(AppointmentStatusChangedEvent event);

    List<AuditLogDTO> getAllAuditLogs();

    AuditLogDTO getAuditLogById(Long id);

    List<AuditLogDTO> getAuditLogsByPatientId(Long patientId);

    List<AuditLogDTO> getAuditLogsByAppointmentId(Long appointmentId);

    List<AuditLogDTO> getAuditLogsByEventType(String eventType);
}