package jjose.dev.com.audit.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    private String sourceService;
    private String entityType;
    private Long entityId;

    private String description;

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;

    private String previousStatus;
    private String newStatus;

    private LocalDateTime createdAt;
}