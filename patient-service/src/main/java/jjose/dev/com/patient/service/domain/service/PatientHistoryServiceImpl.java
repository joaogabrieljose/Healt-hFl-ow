package jjose.dev.com.patient.service.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.patient.service.domain.entity.Patient;
import jjose.dev.com.patient.service.domain.entity.PatientHistory;
import jjose.dev.com.patient.service.domain.repository.PatientHistoryRepository;
import jjose.dev.com.patient.service.domain.repository.PatientRepository;
import jjose.dev.com.patient.service.dto.PatientHistoryService;
import jjose.dev.com.patient.service.dto.patientHistoryDTO.PatientHistoryDTO;


@Service
public class PatientHistoryServiceImpl implements PatientHistoryService {

    private final PatientHistoryRepository patientHistoryRepository;
    private final PatientRepository patientRepository;

    public PatientHistoryServiceImpl(
            PatientHistoryRepository patientHistoryRepository,
            PatientRepository patientRepository
    ) {
        this.patientHistoryRepository = patientHistoryRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientHistoryDTO createHistory(Long patientId, PatientHistoryDTO dto) {

        // Buscar o paciente primeiro
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + patientId));

        PatientHistory history = toEntity(dto);

        // Ligar o histórico ao paciente
        history.setPatient(patient);
        history.setCreatedAt(LocalDateTime.now());

        PatientHistory savedHistory = patientHistoryRepository.save(history);

        return toDTO(savedHistory);
    }

    @Override
    public List<PatientHistoryDTO> getHistoriesByPatientId(Long patientId) {

        // Verifica se o paciente existe
        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Paciente não encontrado com ID: " + patientId);
        }

        return patientHistoryRepository.findByPatientIdOrderByEventDateDesc(patientId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public PatientHistoryDTO getHistoryById(Long id) {
        PatientHistory history = patientHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado com ID: " + id));

        return toDTO(history);
    }

    @Override
    public PatientHistoryDTO updateHistory(Long id, PatientHistoryDTO dto) {
        PatientHistory history = patientHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado com ID: " + id));

        history.setEventType(dto.eventType());
        history.setDescription(dto.description());
        history.setEventDate(dto.eventDate());

        PatientHistory updatedHistory = patientHistoryRepository.save(history);

        return toDTO(updatedHistory);
    }

    @Override
    public void deleteHistory(Long id) {
        PatientHistory history = patientHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado com ID: " + id));

        patientHistoryRepository.delete(history);
    }

    private PatientHistory toEntity(PatientHistoryDTO dto) {
        PatientHistory history = new PatientHistory();

        history.setEventType(dto.eventType());
        history.setDescription(dto.description());
        history.setEventDate(dto.eventDate());

        return history;
    }

    private PatientHistoryDTO toDTO(PatientHistory history) {
        return new PatientHistoryDTO(
                history.getId(),
                history.getEventType(),
                history.getDescription(),
                history.getEventDate()
        );
    }
}