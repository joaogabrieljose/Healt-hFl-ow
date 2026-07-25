package jjose.dev.com.notification.domain.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.notification.domain.entity.Notification;
import jjose.dev.com.notification.domain.repository.NotificationRepository;
import jjose.dev.com.notification.domain.service.NotificationService;
import jjose.dev.com.notification.dto.NotificationDTO;
import jjose.dev.com.notification.events.AppointmentCreatedEvent;
import jjose.dev.com.notification.events.AppointmentStatusChangedEvent;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void saveAppointmentCreatedNotification(AppointmentCreatedEvent event) {
        Notification notification = new Notification();

        notification.setType("APPOINTMENT_CREATED");
        notification.setChannel("EMAIL_SMS_SIMULATED");
        notification.setRecipient("paciente-" + event.patientId() + "@healthflow.com");
        notification.setSubject("Consulta marcada com sucesso - HealthFlow");

        notification.setMessage(
                "A consulta " + event.appointmentId()
                        + " foi marcada para o dia "
                        + event.appointmentDate()
                        + " das "
                        + event.startTime()
                        + " às "
                        + event.endTime()
                        + ". Motivo: "
                        + event.reason()
        );

        notification.setStatus("SENT");
        notification.setAppointmentId(event.appointmentId());
        notification.setPatientId(event.patientId());
        notification.setDoctorId(event.doctorId());
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public void saveAppointmentStatusChangedNotification(AppointmentStatusChangedEvent event) {
        Notification notification = new Notification();

        notification.setType("APPOINTMENT_STATUS_CHANGED");
        notification.setChannel("EMAIL_SMS_SIMULATED");
        notification.setRecipient("paciente-" + event.patientId() + "@healthflow.com");
        notification.setSubject("Estado da consulta atualizado - HealthFlow");

        notification.setMessage(
                "A consulta " + event.appointmentId()
                        + " mudou de estado: "
                        + event.previousStatus()
                        + " -> "
                        + event.newStatus()
                        + ". Motivo: "
                        + event.reason()
        );

        notification.setStatus("SENT");
        notification.setAppointmentId(event.appointmentId());
        notification.setPatientId(event.patientId());
        notification.setDoctorId(event.doctorId());
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public NotificationDTO getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada com ID: " + id));

        return toDTO(notification);
    }

    @Override
    public List<NotificationDTO> getNotificationsByPatientId(Long patientId) {
        return notificationRepository.findByPatientId(patientId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<NotificationDTO> getNotificationsByAppointmentId(Long appointmentId) {
        return notificationRepository.findByAppointmentId(appointmentId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private NotificationDTO toDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getType(),
                notification.getChannel(),
                notification.getRecipient(),
                notification.getSubject(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getAppointmentId(),
                notification.getPatientId(),
                notification.getDoctorId(),
                notification.getCreatedAt()
        );
    }
}