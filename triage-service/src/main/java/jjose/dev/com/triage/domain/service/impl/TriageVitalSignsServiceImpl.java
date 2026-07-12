package jjose.dev.com.triage.domain.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jjose.dev.com.triage.domain.entity.Triage;
import jjose.dev.com.triage.domain.entity.TriageVitalSigns;
import jjose.dev.com.triage.domain.repository.TriageRepository;
import jjose.dev.com.triage.domain.repository.TriageVitalSignsRepository;
import jjose.dev.com.triage.domain.service.TriageVitalSignsService;
import jjose.dev.com.triage.dto.TriageVitalSignsDTO;

@Service
public class TriageVitalSignsServiceImpl implements TriageVitalSignsService {

    private final TriageVitalSignsRepository vitalSignsRepository;
    private final TriageRepository triageRepository;

    public TriageVitalSignsServiceImpl(
            TriageVitalSignsRepository vitalSignsRepository,
            TriageRepository triageRepository
    ) {
        this.vitalSignsRepository = vitalSignsRepository;
        this.triageRepository = triageRepository;
    }

    @Override
    public TriageVitalSignsDTO createVitalSigns(Long triageId, TriageVitalSignsDTO dto) {

        Triage triage = triageRepository.findById(triageId)
                .orElseThrow(() -> new RuntimeException("Triagem não encontrada com ID: " + triageId));

        // Como a relação é 1 para 1, uma triagem só pode ter um registo de sinais vitais
        if (vitalSignsRepository.existsByTriageId(triageId)) {
            throw new RuntimeException("Esta triagem já possui sinais vitais registados.");
        }

        TriageVitalSigns vitalSigns = toEntity(dto);

        vitalSigns.setTriage(triage);

        if (vitalSigns.getMeasuredAt() == null) {
            vitalSigns.setMeasuredAt(LocalDateTime.now());
        }

        TriageVitalSigns savedVitalSigns = vitalSignsRepository.save(vitalSigns);

        return toDTO(savedVitalSigns);
    }

    @Override
    public TriageVitalSignsDTO getVitalSignsByTriageId(Long triageId) {
        TriageVitalSigns vitalSigns = vitalSignsRepository.findByTriageId(triageId)
                .orElseThrow(() -> new RuntimeException("Sinais vitais não encontrados para a triagem ID: " + triageId));

        return toDTO(vitalSigns);
    }

    @Override
    public TriageVitalSignsDTO getVitalSignsById(Long id) {
        TriageVitalSigns vitalSigns = vitalSignsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sinais vitais não encontrados com ID: " + id));

        return toDTO(vitalSigns);
    }

    @Override
    public TriageVitalSignsDTO updateVitalSigns(Long id, TriageVitalSignsDTO dto) {
        TriageVitalSigns vitalSigns = vitalSignsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sinais vitais não encontrados com ID: " + id));

        vitalSigns.setTemperature(dto.temperature());
        vitalSigns.setHeartRate(dto.heartRate());
        vitalSigns.setRespiratoryRate(dto.respiratoryRate());
        vitalSigns.setBloodPressure(dto.bloodPressure());
        vitalSigns.setOxygenSaturation(dto.oxygenSaturation());
        vitalSigns.setWeight(dto.weight());
        vitalSigns.setHeight(dto.height());

        if (dto.measuredAt() != null) {
            vitalSigns.setMeasuredAt(dto.measuredAt());
        }

        TriageVitalSigns updatedVitalSigns = vitalSignsRepository.save(vitalSigns);

        return toDTO(updatedVitalSigns);
    }

    @Override
    public void deleteVitalSigns(Long id) {
        TriageVitalSigns vitalSigns = vitalSignsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sinais vitais não encontrados com ID: " + id));

        vitalSignsRepository.delete(vitalSigns);
    }

    private TriageVitalSigns toEntity(TriageVitalSignsDTO dto) {
        TriageVitalSigns vitalSigns = new TriageVitalSigns();

        vitalSigns.setTemperature(dto.temperature());
        vitalSigns.setHeartRate(dto.heartRate());
        vitalSigns.setRespiratoryRate(dto.respiratoryRate());
        vitalSigns.setBloodPressure(dto.bloodPressure());
        vitalSigns.setOxygenSaturation(dto.oxygenSaturation());
        vitalSigns.setWeight(dto.weight());
        vitalSigns.setHeight(dto.height());
        vitalSigns.setMeasuredAt(dto.measuredAt());

        return vitalSigns;
    }

    private TriageVitalSignsDTO toDTO(TriageVitalSigns vitalSigns) {
        return new TriageVitalSignsDTO(
                vitalSigns.getId(),
                vitalSigns.getTriage() != null ? vitalSigns.getTriage().getId() : null,
                vitalSigns.getTemperature(),
                vitalSigns.getHeartRate(),
                vitalSigns.getRespiratoryRate(),
                vitalSigns.getBloodPressure(),
                vitalSigns.getOxygenSaturation(),
                vitalSigns.getWeight(),
                vitalSigns.getHeight(),
                vitalSigns.getMeasuredAt()
        );
    }
}