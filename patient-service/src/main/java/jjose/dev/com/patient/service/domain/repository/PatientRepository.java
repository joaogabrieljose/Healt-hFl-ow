package jjose.dev.com.patient.service.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jjose.dev.com.patient.service.domain.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByDocumentNumber(String documentNumber);

    Optional<Patient> findByFullName(String fullName);

    boolean existsByDocumentNumber(String documentNumber);
    
} 
