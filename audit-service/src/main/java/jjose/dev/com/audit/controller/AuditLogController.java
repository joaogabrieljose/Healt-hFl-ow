package jjose.dev.com.audit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jjose.dev.com.audit.domain.service.AuditLogService;
import jjose.dev.com.audit.dto.AuditLogDTO;

@RestController
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/audit-logs")
    public List<AuditLogDTO> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    @GetMapping("/audit-logs/{id}")
    public AuditLogDTO getAuditLogById(@PathVariable Long id) {
        return auditLogService.getAuditLogById(id);
    }

    @GetMapping("/audit-logs/patient/{patientId}")
    public List<AuditLogDTO> getAuditLogsByPatientId(@PathVariable Long patientId) {
        return auditLogService.getAuditLogsByPatientId(patientId);
    }

    @GetMapping("/audit-logs/appointment/{appointmentId}")
    public List<AuditLogDTO> getAuditLogsByAppointmentId(@PathVariable Long appointmentId) {
        return auditLogService.getAuditLogsByAppointmentId(appointmentId);
    }

    @GetMapping("/audit-logs/event-type/{eventType}")
    public List<AuditLogDTO> getAuditLogsByEventType(@PathVariable String eventType) {
        return auditLogService.getAuditLogsByEventType(eventType);
    }
}