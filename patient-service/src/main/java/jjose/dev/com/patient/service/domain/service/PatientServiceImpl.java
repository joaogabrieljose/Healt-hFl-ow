package jjose.dev.com.patient.service.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.patient.service.domain.entity.Patient;
import jjose.dev.com.patient.service.domain.repository.PatientRepository;
import jjose.dev.com.patient.service.dto.PatientService;
import jjose.dev.com.patient.service.dto.patientDTO.PatientDTO;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientDTO createPatient(PatientDTO dto) {

        if (patientRepository.existsByDocumentNumber(dto.documentNumber())) {
            throw new RuntimeException("Já existe um paciente com este número de documento.");
        }

        Patient patient = toEntity(dto);

        patient.setCreatedAt(LocalDateTime.now());
        patient.setUpdatedAt(LocalDateTime.now());

        Patient savedPatient = patientRepository.save(patient);

        return toDTO(savedPatient);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));

        return toDTO(patient);
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));

        patient.setFullName(dto.fullName());
        patient.setBirthDate(dto.birthDate());
        patient.setGender(dto.gender());
        patient.setDocumentNumber(dto.documentNumber());
        patient.setNationality(dto.nationality());
        patient.setAddress(dto.address());
        patient.setUpdatedAt(LocalDateTime.now());

        Patient updatedPatient = patientRepository.save(patient);

        return toDTO(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));

        patientRepository.delete(patient);
    }

    private Patient toEntity(PatientDTO dto) {
        Patient patient = new Patient();

        patient.setFullName(dto.fullName());
        patient.setBirthDate(dto.birthDate());
        patient.setGender(dto.gender());
        patient.setDocumentNumber(dto.documentNumber());
        patient.setNationality(dto.nationality());
        patient.setAddress(dto.address());

        return patient;
    }

    private PatientDTO toDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getFullName(),
                patient.getBirthDate(),
                patient.getGender(),
                patient.getDocumentNumber(),
                patient.getNationality(),
                patient.getAddress()
        );
    }
}