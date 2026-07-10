package jjose.dev.com.doctor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.doctor.dto.SpecialtyService;
import jjose.dev.com.doctor.dto.specialtyDTO.SpecialtyDTO;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public ResponseEntity<SpecialtyDTO> createSpecialty(@RequestBody SpecialtyDTO dto) {
        SpecialtyDTO createdSpecialty = specialtyService.createSpecialty(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpecialty);
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> getAllSpecialties() {
        List<SpecialtyDTO> specialties = specialtyService.getAllSpecialties();
        return ResponseEntity.ok(specialties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> getSpecialtyById(@PathVariable Long id) {
        SpecialtyDTO specialty = specialtyService.getSpecialtyById(id);
        return ResponseEntity.ok(specialty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> updateSpecialty(
            @PathVariable Long id,
            @RequestBody SpecialtyDTO dto
    ) {
        SpecialtyDTO updatedSpecialty = specialtyService.updateSpecialty(id, dto);
        return ResponseEntity.ok(updatedSpecialty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.noContent().build();
    }
}