package jjose.dev.com.triage.domain.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.triage.domain.entity.Triage;
import jjose.dev.com.triage.domain.repository.TriageRepository;
import jjose.dev.com.triage.domain.service.TriageService;
import jjose.dev.com.triage.dto.TriageDTO;


@Service
public class TriageServiceImpl implements TriageService {

    private final TriageRepository triageRepository;

    public TriageServiceImpl(TriageRepository triageRepository) {
        this.triageRepository = triageRepository;
    }

    @Override
    public TriageDTO createTriage(TriageDTO dto) {

        // Uma consulta/agendamento deve ter apenas uma triagem
        if (triageRepository.existsByAppointmentId(dto.appointmentId())) {
            throw new RuntimeException("Já existe uma triagem para esta consulta.");
        }

        Triage triage = toEntity(dto);

        triage.setCreatedAt(LocalDateTime.now());
        triage.setUpdatedAt(LocalDateTime.now());

        Triage savedTriage = triageRepository.save(triage);

        return toDTO(savedTriage);
    }

    @Override
    public List<TriageDTO> getAllTriages() {
        return triageRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public TriageDTO getTriageById(Long id) {
        Triage triage = triageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Triagem não encontrada com ID: " + id));

        return toDTO(triage);
    }

    @Override
    public List<TriageDTO> getTriagesByPatientId(Long patientId) {
        return triageRepository.findByPatientId(patientId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public TriageDTO getTriageByAppointmentId(Long appointmentId) {
        Triage triage = triageRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new RuntimeException("Triagem não encontrada para a consulta ID: " + appointmentId));

        return toDTO(triage);
    }

    @Override
    public List<TriageDTO> getTriagesByUrgencyLevel(String urgencyLevel) {
        return triageRepository.findByUrgencyLevel(urgencyLevel)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public TriageDTO updateTriage(Long id, TriageDTO dto) {
        Triage triage = triageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Triagem não encontrada com ID: " + id));

        triage.setPatientId(dto.patientId());
        triage.setAppointmentId(dto.appointmentId());
        triage.setMainComplaint(dto.mainComplaint());
        triage.setSymptoms(dto.symptoms());
        triage.setUrgencyLevel(dto.urgencyLevel());
        triage.setNotes(dto.notes());
        triage.setUpdatedAt(LocalDateTime.now());

        Triage updatedTriage = triageRepository.save(triage);

        return toDTO(updatedTriage);
    }

    @Override
    public void deleteTriage(Long id) {
        Triage triage = triageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Triagem não encontrada com ID: " + id));

        triageRepository.delete(triage);
    }

    private Triage toEntity(TriageDTO dto) {
        Triage triage = new Triage();

        triage.setPatientId(dto.patientId());
        triage.setAppointmentId(dto.appointmentId());
        triage.setMainComplaint(dto.mainComplaint());
        triage.setSymptoms(dto.symptoms());
        triage.setUrgencyLevel(dto.urgencyLevel());
        triage.setNotes(dto.notes());

        return triage;
    }

    private TriageDTO toDTO(Triage triage) {
        return new TriageDTO(
                triage.getId(),
                triage.getPatientId(),
                triage.getAppointmentId(),
                triage.getMainComplaint(),
                triage.getSymptoms(),
                triage.getUrgencyLevel(),
                triage.getNotes()
        );
    }
}