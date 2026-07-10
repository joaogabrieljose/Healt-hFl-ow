package jjose.dev.com.doctor.dto;

import java.util.List;

import jjose.dev.com.doctor.dto.specialtyDTO.SpecialtyDTO;

public interface SpecialtyService {

    SpecialtyDTO createSpecialty(SpecialtyDTO dto);

    List<SpecialtyDTO> getAllSpecialties();

    SpecialtyDTO getSpecialtyById(Long id);

    SpecialtyDTO updateSpecialty(Long id, SpecialtyDTO dto);

    void deleteSpecialty(Long id);
}