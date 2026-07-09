package jjose.dev.com.patient.service.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jjose.dev.com.patient.service.domain.entity.PatientContact;

public interface PatientContactRepository extends JpaRepository<PatientContact, Long>{
    List<PatientContact> findByPatientId(Long patient);
}
