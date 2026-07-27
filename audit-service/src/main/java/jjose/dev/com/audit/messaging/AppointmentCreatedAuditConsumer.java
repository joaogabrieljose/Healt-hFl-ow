package jjose.dev.com.audit.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import jjose.dev.com.audit.domain.service.AuditLogService;
import jjose.dev.com.audit.events.AppointmentCreatedEvent;

@Component
public class AppointmentCreatedAuditConsumer {

    private final AuditLogService auditLogService;

    public AppointmentCreatedAuditConsumer(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @RabbitListener(queues = "audit.appointment.created.queue")
    public void consumeAppointmentCreated(AppointmentCreatedEvent event) {

        System.out.println("======================================");
        System.out.println("AUDIT-SERVICE RECEBEU EVENTO DE CONSULTA CRIADA");
        System.out.println("Appointment ID: " + event.appointmentId());
        System.out.println("Patient ID: " + event.patientId());
        System.out.println("Doctor ID: " + event.doctorId());
        System.out.println("Data: " + event.appointmentDate());
        System.out.println("Hora: " + event.startTime() + " - " + event.endTime());
        System.out.println("Estado: " + event.status());
        System.out.println("======================================");

        auditLogService.saveAppointmentCreatedAudit(event);
    }
}