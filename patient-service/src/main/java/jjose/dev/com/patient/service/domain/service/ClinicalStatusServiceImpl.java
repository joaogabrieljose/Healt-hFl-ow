package jjose.dev.com.patient.service.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jjose.dev.com.patient.service.domain.entity.ClinicalStatus;
import jjose.dev.com.patient.service.domain.entity.Patient;
import jjose.dev.com.patient.service.domain.repository.ClinicalStatusRepository;
import jjose.dev.com.patient.service.domain.repository.PatientRepository;
import jjose.dev.com.patient.service.dto.ClinicalStatusService;
import jjose.dev.com.patient.service.dto.patientStatusDTO.ClinicalStatusDTO;


@Service
public class ClinicalStatusServiceImpl implements ClinicalStatusService {

    private final ClinicalStatusRepository clinicalStatusRepository;
    private final PatientRepository patientRepository;

    public ClinicalStatusServiceImpl(
            ClinicalStatusRepository clinicalStatusRepository,
            PatientRepository patientRepository
    ) {
        this.clinicalStatusRepository = clinicalStatusRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public ClinicalStatusDTO createClinicalStatus(Long patientId, ClinicalStatusDTO dto) {

        // Verifica se o paciente existe
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + patientId));

        // Como a relação é 1 para 1, o paciente não pode ter mais de um estado clínico
        if (clinicalStatusRepository.existsByPatientId(patientId)) {
            throw new RuntimeException("Este paciente já possui um estado clínico registado.");
        }

        ClinicalStatus clinicalStatus = toEntity(dto);

        // Liga o estado clínico ao paciente
        clinicalStatus.setPatient(patient);
        clinicalStatus.setUpdatedAt(LocalDateTime.now());

        ClinicalStatus savedClinicalStatus = clinicalStatusRepository.save(clinicalStatus);

        return toDTO(savedClinicalStatus);
    }

    @Override
    public ClinicalStatusDTO getClinicalStatusByPatientId(Long patientId) {

        // Verifica se o paciente existe
        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Paciente não encontrado com ID: " + patientId);
        }

        ClinicalStatus clinicalStatus = clinicalStatusRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Estado clínico não encontrado para o paciente ID: " + patientId));

        return toDTO(clinicalStatus);
    }

    @Override
    public ClinicalStatusDTO getClinicalStatusById(Long id) {
        ClinicalStatus clinicalStatus = clinicalStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado clínico não encontrado com ID: " + id));

        return toDTO(clinicalStatus);
    }

    @Override
    public ClinicalStatusDTO updateClinicalStatus(Long id, ClinicalStatusDTO dto) {
        ClinicalStatus clinicalStatus = clinicalStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado clínico não encontrado com ID: " + id));

        clinicalStatus.setBloodType(dto.bloodType());
        clinicalStatus.setAllergies(dto.allergies());
        clinicalStatus.setChronicDiseases(dto.chronicDiseases());
        clinicalStatus.setCurrentMedications(dto.currentMedications());
        clinicalStatus.setClinicalNotes(dto.clinicalNotes());
        clinicalStatus.setStatus(dto.status());
        clinicalStatus.setUpdatedAt(LocalDateTime.now());

        ClinicalStatus updatedClinicalStatus = clinicalStatusRepository.save(clinicalStatus);

        return toDTO(updatedClinicalStatus);
    }

    @Override
    public void deleteClinicalStatus(Long id) {
        ClinicalStatus clinicalStatus = clinicalStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado clínico não encontrado com ID: " + id));

        clinicalStatusRepository.delete(clinicalStatus);
    }

    private ClinicalStatus toEntity(ClinicalStatusDTO dto) {
        ClinicalStatus clinicalStatus = new ClinicalStatus();

        clinicalStatus.setBloodType(dto.bloodType());
        clinicalStatus.setAllergies(dto.allergies());
        clinicalStatus.setChronicDiseases(dto.chronicDiseases());
        clinicalStatus.setCurrentMedications(dto.currentMedications());
        clinicalStatus.setClinicalNotes(dto.clinicalNotes());
        clinicalStatus.setStatus(dto.status());

        return clinicalStatus;
    }

    private ClinicalStatusDTO toDTO(ClinicalStatus clinicalStatus) {
        return new ClinicalStatusDTO(
                clinicalStatus.getId(),
                clinicalStatus.getBloodType(),
                clinicalStatus.getAllergies(),
                clinicalStatus.getChronicDiseases(),
                clinicalStatus.getCurrentMedications(),
                clinicalStatus.getClinicalNotes(),
                clinicalStatus.getStatus()
        );
    }
}