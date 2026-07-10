package jjose.dev.com.doctor.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.doctor.domain.entity.Specialty;
import jjose.dev.com.doctor.domain.repository.SpecialtyRepository;
import jjose.dev.com.doctor.dto.specialtyDTO.SpecialtyDTO;
import jjose.dev.com.doctor.dto.SpecialtyService;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public SpecialtyDTO createSpecialty(SpecialtyDTO dto) {

        if (specialtyRepository.existsByName(dto.name())) {
            throw new RuntimeException("Já existe uma especialidade com este nome.");
        }

        Specialty specialty = toEntity(dto);
        specialty.setCreatedAt(LocalDateTime.now());
        specialty.setUpdatedAt(LocalDateTime.now());

        Specialty savedSpecialty = specialtyRepository.save(specialty);

        return toDTO(savedSpecialty);
    }

    @Override
    public List<SpecialtyDTO> getAllSpecialties() {
        return specialtyRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public SpecialtyDTO getSpecialtyById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + id));

        return toDTO(specialty);
    }

    @Override
    public SpecialtyDTO updateSpecialty(Long id, SpecialtyDTO dto) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + id));

        specialty.setName(dto.name());
        specialty.setDescription(dto.description());
        specialty.setUpdatedAt(LocalDateTime.now());

        Specialty updatedSpecialty = specialtyRepository.save(specialty);

        return toDTO(updatedSpecialty);
    }

    @Override
    public void deleteSpecialty(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + id));

        specialtyRepository.delete(specialty);
    }

    private Specialty toEntity(SpecialtyDTO dto) {
        Specialty specialty = new Specialty();

        specialty.setName(dto.name());
        specialty.setDescription(dto.description());

        return specialty;
    }

    private SpecialtyDTO toDTO(Specialty specialty) {
        return new SpecialtyDTO(
                specialty.getId(),
                specialty.getName(),
                specialty.getDescription()
        );
    }
}