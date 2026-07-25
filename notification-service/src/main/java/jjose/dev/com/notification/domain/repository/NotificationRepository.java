package jjose.dev.com.notification.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjose.dev.com.notification.domain.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByPatientId(Long patientId);

    List<Notification> findByAppointmentId(Long appointmentId);

    List<Notification> findByType(String type);

    List<Notification> findByStatus(String status);
}