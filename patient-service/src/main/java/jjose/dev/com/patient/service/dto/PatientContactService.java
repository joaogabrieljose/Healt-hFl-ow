package jjose.dev.com.patient.service.dto;

import java.util.List;

import jjose.dev.com.patient.service.dto.patientContactDTO.PatientContactDTO;

public interface PatientContactService {
    
    PatientContactDTO createContact(Long patientId, PatientContactDTO dto);

    List<PatientContactDTO> getContactsByPatientId(Long patientId);

    PatientContactDTO getContactById(Long id);

    PatientContactDTO updateContact(Long id, PatientContactDTO dto);

    void deleteContact(Long id);
    
}
