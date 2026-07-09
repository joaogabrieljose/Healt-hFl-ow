package jjose.dev.com.patient.service.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jjose.dev.com.patient.service.domain.entity.ClinicalStatus;

public interface ClinicalStatusRepository extends JpaRepository<ClinicalStatus, Long>{
    
    Optional<ClinicalStatus> findByPatientId(Long patientId);

    boolean existsByPatientId(Long patientId);
    
}
