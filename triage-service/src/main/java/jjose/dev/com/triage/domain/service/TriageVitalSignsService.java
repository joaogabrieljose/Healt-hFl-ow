package jjose.dev.com.triage.domain.service;

import jjose.dev.com.triage.dto.TriageVitalSignsDTO;

public interface TriageVitalSignsService {

    TriageVitalSignsDTO createVitalSigns(Long triageId, TriageVitalSignsDTO dto);

    TriageVitalSignsDTO getVitalSignsByTriageId(Long triageId);

    TriageVitalSignsDTO getVitalSignsById(Long id);

    TriageVitalSignsDTO updateVitalSigns(Long id, TriageVitalSignsDTO dto);

    void deleteVitalSigns(Long id);
}