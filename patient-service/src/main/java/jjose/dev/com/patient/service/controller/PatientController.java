package jjose.dev.com.patient.service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.patient.service.dto.PatientService;
import jjose.dev.com.patient.service.dto.patientDTO.PatientDTO;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO dto) {
        PatientDTO createdPatient = patientService.createPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

   
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

 
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientDTO dto
    ) {
        PatientDTO updatedPatient = patientService.updatePatient(id, dto);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}