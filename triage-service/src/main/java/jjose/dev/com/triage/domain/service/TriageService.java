package jjose.dev.com.triage.domain.service;

import java.util.List;

import jjose.dev.com.triage.dto.TriageDTO;



public interface TriageService {

    TriageDTO createTriage(TriageDTO dto);

    List<TriageDTO> getAllTriages();

    TriageDTO getTriageById(Long id);

    List<TriageDTO> getTriagesByPatientId(Long patientId);

    TriageDTO getTriageByAppointmentId(Long appointmentId);

    List<TriageDTO> getTriagesByUrgencyLevel(String urgencyLevel);

    TriageDTO updateTriage(Long id, TriageDTO dto);

    void deleteTriage(Long id);
}