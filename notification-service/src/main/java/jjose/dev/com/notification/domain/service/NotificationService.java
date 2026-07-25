package jjose.dev.com.notification.domain.service;

import java.util.List;

import jjose.dev.com.notification.dto.NotificationDTO;
import jjose.dev.com.notification.events.AppointmentCreatedEvent;
import jjose.dev.com.notification.events.AppointmentStatusChangedEvent;

public interface NotificationService {

    void saveAppointmentCreatedNotification(AppointmentCreatedEvent event);

    void saveAppointmentStatusChangedNotification(AppointmentStatusChangedEvent event);

    List<NotificationDTO> getAllNotifications();

    NotificationDTO getNotificationById(Long id);

    List<NotificationDTO> getNotificationsByPatientId(Long patientId);

    List<NotificationDTO> getNotificationsByAppointmentId(Long appointmentId);
}