package jjose.dev.com.patient.service.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.patient.service.domain.entity.Patient;
import jjose.dev.com.patient.service.domain.entity.PatientContact;
import jjose.dev.com.patient.service.domain.repository.PatientContactRepository;
import jjose.dev.com.patient.service.domain.repository.PatientRepository;
import jjose.dev.com.patient.service.dto.PatientContactService;
import jjose.dev.com.patient.service.dto.patientContactDTO.PatientContactDTO;


@Service
public class PatientContactServiceImpl implements PatientContactService {

    private final PatientContactRepository patientContactRepository;
    private final PatientRepository patientRepository;

    public PatientContactServiceImpl(
            PatientContactRepository patientContactRepository,
            PatientRepository patientRepository
    ) {
        this.patientContactRepository = patientContactRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientContactDTO createContact(Long patientId, PatientContactDTO dto) {

        // Buscar o paciente primeiro
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + patientId));

        PatientContact contact = toEntity(dto);

        // Aqui ligamos o contacto ao paciente
        contact.setPatient(patient);
        contact.setCreatedAt(LocalDateTime.now());

        PatientContact savedContact = patientContactRepository.save(contact);

        return toDTO(savedContact);
    }

    @Override
    public List<PatientContactDTO> getContactsByPatientId(Long patientId) {

        // Verifica se o paciente existe
        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Paciente não encontrado com ID: " + patientId);
        }

        return patientContactRepository.findByPatientId(patientId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public PatientContactDTO getContactById(Long id) {
        PatientContact contact = patientContactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto não encontrado com ID: " + id));

        return toDTO(contact);
    }

    @Override
    public PatientContactDTO updateContact(Long id, PatientContactDTO dto) {
        PatientContact contact = patientContactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto não encontrado com ID: " + id));

        contact.setPhone(dto.phone());
        contact.setEmail(dto.email());
        contact.setEmergencyContactName(dto.emergencyContactName());
        contact.setEmergencyContactPhone(dto.emergencyContactPhone());
        contact.setRelationship(dto.relationship());

        PatientContact updatedContact = patientContactRepository.save(contact);

        return toDTO(updatedContact);
    }

    @Override
    public void deleteContact(Long id) {
        PatientContact contact = patientContactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto não encontrado com ID: " + id));

        patientContactRepository.delete(contact);
    }

    private PatientContact toEntity(PatientContactDTO dto) {
        PatientContact contact = new PatientContact();

        contact.setPhone(dto.phone());
        contact.setEmail(dto.email());
        contact.setEmergencyContactName(dto.emergencyContactName());
        contact.setEmergencyContactPhone(dto.emergencyContactPhone());
        contact.setRelationship(dto.relationship());

        return contact;
    }

    private PatientContactDTO toDTO(PatientContact contact) {
        return new PatientContactDTO(
                contact.getId(),
                contact.getPhone(),
                contact.getEmail(),
                contact.getEmergencyContactName(),
                contact.getEmergencyContactPhone(),
                contact.getRelationship()
        );
    }
}