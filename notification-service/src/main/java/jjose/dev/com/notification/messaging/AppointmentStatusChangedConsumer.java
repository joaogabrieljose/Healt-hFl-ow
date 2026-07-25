package jjose.dev.com.notification.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import jjose.dev.com.notification.domain.service.NotificationService;
import jjose.dev.com.notification.events.AppointmentStatusChangedEvent;

@Component
public class AppointmentStatusChangedConsumer {

    private final NotificationService notificationService;

    public AppointmentStatusChangedConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "notification.appointment.status.changed.queue")
    public void consumeAppointmentStatusChanged(AppointmentStatusChangedEvent event) {

        System.out.println("======================================");
        System.out.println("ALTERAÇÃO DE ESTADO RECEBIDA PELO NOTIFICATION-SERVICE");
        System.out.println("Appointment ID: " + event.appointmentId());
        System.out.println("Patient ID: " + event.patientId());
        System.out.println("Doctor ID: " + event.doctorId());
        System.out.println("Estado anterior: " + event.previousStatus());
        System.out.println("Novo estado: " + event.newStatus());
        System.out.println("Motivo: " + event.reason());
        System.out.println("Alterado em: " + event.changedAt());
        System.out.println("======================================");

        notificationService.saveAppointmentStatusChangedNotification(event);
    }
}