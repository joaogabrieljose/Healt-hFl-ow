package jjose.dev.com.patient.service.domain.service.dto;

import java.util.List;

import jjose.dev.com.patient.service.domain.service.dto.patientDTO.PatientDTO;

public interface PatientService {
    
    PatientDTO createPatient(PatientDTO dto);

    List<PatientDTO> getAllPatients();

    PatientDTO getPatientById(Long id);

    PatientDTO updatePatient(Long id, PatientDTO dto);

    void deletePatient(Long id);
}
