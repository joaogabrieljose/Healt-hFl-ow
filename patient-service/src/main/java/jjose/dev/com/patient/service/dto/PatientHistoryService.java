package jjose.dev.com.patient.service.dto;

import java.util.List;

import jjose.dev.com.patient.service.dto.patientHistoryDTO.PatientHistoryDTO;

public interface PatientHistoryService {
    
    PatientHistoryDTO createHistory(Long patientId, PatientHistoryDTO dto);

    List<PatientHistoryDTO> getHistoriesByPatientId(Long patientId);

    PatientHistoryDTO getHistoryById(Long id);

    PatientHistoryDTO updateHistory(Long id, PatientHistoryDTO dto);

    void deleteHistory(Long id);
    
}
