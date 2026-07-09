package jjose.dev.com.patient.service.dto;

import jjose.dev.com.patient.service.dto.patientStatusDTO.ClinicalStatusDTO;

public interface ClinicalStatusService {
    

    ClinicalStatusDTO createClinicalStatus(Long patientId, ClinicalStatusDTO dto);

    ClinicalStatusDTO getClinicalStatusByPatientId(Long patientId);

    ClinicalStatusDTO getClinicalStatusById(Long id);

    ClinicalStatusDTO updateClinicalStatus(Long id, ClinicalStatusDTO dto);

    void deleteClinicalStatus(Long id);
}
