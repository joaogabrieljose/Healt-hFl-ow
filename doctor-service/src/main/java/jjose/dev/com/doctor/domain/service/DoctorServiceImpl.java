package main.java.jjose.dev.com.doctor.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import jjose.dev.com.doctor.domain.entity.Doctor;
import jjose.dev.com.doctor.domain.entity.Specialty;
import jjose.dev.com.doctor.domain.service.DoctorService;
import jjose.dev.com.doctor.dto.doctorDTO.DoctorDTO;
import jjose.dev.com.doctor.repository.DoctorRepository;
import jjose.dev.com.doctor.repository.SpecialtyRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;

    public DoctorServiceImpl(
            DoctorRepository doctorRepository,
            SpecialtyRepository specialtyRepository
    ) {
        this.doctorRepository = doctorRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO dto) {

        if (doctorRepository.existsByLicenseNumber(dto.licenseNumber())) {
            throw new RuntimeException("Já existe um médico com este número de licença.");
        }

        if (doctorRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Já existe um médico com este email.");
        }

        Specialty specialty = specialtyRepository.findById(dto.specialtyId())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + dto.specialtyId()));

        Doctor doctor = toEntity(dto);
        doctor.setSpecialty(specialty);
        doctor.setCreatedAt(LocalDateTime.now());
        doctor.setUpdatedAt(LocalDateTime.now());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return toDTO(savedDoctor);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com ID: " + id));

        return toDTO(doctor);
    }

    @Override
    public List<DoctorDTO> getDoctorsBySpecialty(Long specialtyId) {
        return doctorRepository.findBySpecialtyId(specialtyId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<DoctorDTO> getDoctorsByStatus(String status) {
        return doctorRepository.findByStatus(status)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com ID: " + id));

        Specialty specialty = specialtyRepository.findById(dto.specialtyId())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com ID: " + dto.specialtyId()));

        doctor.setFullName(dto.fullName());
        doctor.setLicenseNumber(dto.licenseNumber());
        doctor.setEmail(dto.email());
        doctor.setPhone(dto.phone());
        doctor.setGender(dto.gender());
        doctor.setStatus(dto.status());
        doctor.setSpecialty(specialty);
        doctor.setUpdatedAt(LocalDateTime.now());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return toDTO(updatedDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com ID: " + id));

        doctorRepository.delete(doctor);
    }

    private Doctor toEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();

        doctor.setFullName(dto.fullName());
        doctor.setLicenseNumber(dto.licenseNumber());
        doctor.setEmail(dto.email());
        doctor.setPhone(dto.phone());
        doctor.setGender(dto.gender());
        doctor.setStatus(dto.status());

        return doctor;
    }

    private DoctorDTO toDTO(Doctor doctor) {
        return new DoctorDTO(
                doctor.getId(),
                doctor.getFullName(),
                doctor.getLicenseNumber(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getGender(),
                doctor.getStatus(),
                doctor.getSpecialty() != null ? doctor.getSpecialty().getId() : null,
                doctor.getSpecialty() != null ? doctor.getSpecialty().getName() : null
        );
    }
}