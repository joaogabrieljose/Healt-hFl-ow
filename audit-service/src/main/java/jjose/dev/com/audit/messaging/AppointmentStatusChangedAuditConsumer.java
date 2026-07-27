package jjose.dev.com.audit.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import jjose.dev.com.audit.domain.service.AuditLogService;
import jjose.dev.com.audit.events.AppointmentStatusChangedEvent;

@Component
public class AppointmentStatusChangedAuditConsumer {

    private final AuditLogService auditLogService;

    public AppointmentStatusChangedAuditConsumer(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @RabbitListener(queues = "audit.appointment.status.changed.queue")
    public void consumeAppointmentStatusChanged(AppointmentStatusChangedEvent event) {

        System.out.println("======================================");
        System.out.println("AUDIT-SERVICE RECEBEU EVENTO DE ALTERAÇÃO DE ESTADO");
        System.out.println("Appointment ID: " + event.appointmentId());
        System.out.println("Patient ID: " + event.patientId());
        System.out.println("Doctor ID: " + event.doctorId());
        System.out.println("Estado anterior: " + event.previousStatus());
        System.out.println("Novo estado: " + event.newStatus());
        System.out.println("Motivo: " + event.reason());
        System.out.println("======================================");

        auditLogService.saveAppointmentStatusChangedAudit(event);
    }
}