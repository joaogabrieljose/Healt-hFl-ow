package jjose.dev.com.patient.service.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jjose.dev.com.patient.service.domain.entity.PatientHistory;

public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Long>{
    
     List<PatientHistory> findByPatientId(Long patientId);

    List<PatientHistory> findByPatientIdOrderByEventDateDesc(Long patientId);
}
