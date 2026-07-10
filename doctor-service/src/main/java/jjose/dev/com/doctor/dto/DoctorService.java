package main.java.jjose.dev.com.doctor.dto;

import java.util.List;

import jjose.dev.com.doctor.dto.doctorDTO.DoctorDTO;

public interface DoctorService {

    DoctorDTO createDoctor(DoctorDTO dto);

    List<DoctorDTO> getAllDoctors();

    DoctorDTO getDoctorById(Long id);

    List<DoctorDTO> getDoctorsBySpecialty(Long specialtyId);

    List<DoctorDTO> getDoctorsByStatus(String status);

    DoctorDTO updateDoctor(Long id, DoctorDTO dto);

    void deleteDoctor(Long id);
}