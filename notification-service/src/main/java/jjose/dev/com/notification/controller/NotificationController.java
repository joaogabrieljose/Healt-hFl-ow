package jjose.dev.com.notification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jjose.dev.com.notification.domain.service.NotificationService;
import jjose.dev.com.notification.dto.NotificationDTO;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public List<NotificationDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/notifications/{id}")
    public NotificationDTO getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/notifications/patient/{patientId}")
    public List<NotificationDTO> getNotificationsByPatientId(@PathVariable Long patientId) {
        return notificationService.getNotificationsByPatientId(patientId);
    }

    @GetMapping("/notifications/appointment/{appointmentId}")
    public List<NotificationDTO> getNotificationsByAppointmentId(@PathVariable Long appointmentId) {
        return notificationService.getNotificationsByAppointmentId(appointmentId);
    }
}