package jjose.dev.com.doctor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.doctor.dto.DoctorService;
import jjose.dev.com.doctor.dto.doctorDTO.DoctorDTO;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO dto) {
        DoctorDTO createdDoctor = doctorService.createDoctor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/specialty/{specialtyId}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(
            @PathVariable Long specialtyId
    ) {
        List<DoctorDTO> doctors = doctorService.getDoctorsBySpecialty(specialtyId);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByStatus(
            @PathVariable String status
    ) {
        List<DoctorDTO> doctors = doctorService.getDoctorsByStatus(status);
        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorDTO dto
    ) {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(id, dto);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}