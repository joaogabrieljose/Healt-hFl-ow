package main.java.jjose.dev.com.notification.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import jjose.dev.com.notification.events.AppointmentCreatedEvent;

@Component
public class AppointmentCreatedConsumer {

    @RabbitListener(queues = "notification.appointment.created.queue")
    public void consumeAppointmentCreated(AppointmentCreatedEvent event) {

        System.out.println("======================================");
        System.out.println("NOVA CONSULTA RECEBIDA PELO NOTIFICATION-SERVICE");
        System.out.println("Appointment ID: " + event.appointmentId());
        System.out.println("Patient ID: " + event.patientId());
        System.out.println("Doctor ID: " + event.doctorId());
        System.out.println("Data: " + event.appointmentDate());
        System.out.println("Hora: " + event.startTime() + " - " + event.endTime());
        System.out.println("Motivo: " + event.reason());
        System.out.println("Estado: " + event.status());
        System.out.println("Criada em: " + event.createdAt());
        System.out.println("======================================");
    }
}