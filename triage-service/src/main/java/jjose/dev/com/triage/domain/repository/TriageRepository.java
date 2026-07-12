package jjose.dev.com.triage.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjose.dev.com.triage.domain.entity.Triage;

@Repository
public interface TriageRepository extends JpaRepository<Triage, Long> {

    List<Triage> findByPatientId(Long patientId);

    Optional<Triage> findByAppointmentId(Long appointmentId);

    List<Triage> findByUrgencyLevel(String urgencyLevel);

    boolean existsByAppointmentId(Long appointmentId);
}