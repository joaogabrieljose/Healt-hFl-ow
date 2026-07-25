package jjose.dev.com.notification.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String channel;
    private String recipient;
    private String subject;
    private String message;
    private String status;

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;

    private LocalDateTime createdAt;
}