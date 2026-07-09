package jjose.dev.com.patient.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.patient.service.dto.ClinicalStatusService;
import jjose.dev.com.patient.service.dto.patientStatusDTO.ClinicalStatusDTO;

@RestController
public class ClinicalStatusController {

    private final ClinicalStatusService clinicalStatusService;

    public ClinicalStatusController(ClinicalStatusService clinicalStatusService) {
        this.clinicalStatusService = clinicalStatusService;
    }

    // Criar estado clínico para um paciente
    @PostMapping("/patients/{patientId}/clinical-status")
    public ResponseEntity<ClinicalStatusDTO> createClinicalStatus(@PathVariable Long patientId,@RequestBody ClinicalStatusDTO dto) {
        ClinicalStatusDTO createdStatus = clinicalStatusService.createClinicalStatus(patientId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    // Buscar estado clínico de um paciente pelo patientId
    @GetMapping("/patients/{patientId}/clinical-status")
    public ResponseEntity<ClinicalStatusDTO> getClinicalStatusByPatientId(
            @PathVariable Long patientId
    ) {
        ClinicalStatusDTO clinicalStatus = clinicalStatusService.getClinicalStatusByPatientId(patientId);
        return ResponseEntity.ok(clinicalStatus);
    }

    // Buscar estado clínico pelo ID do próprio estado clínico
    @GetMapping("/clinical-status/{id}")
    public ResponseEntity<ClinicalStatusDTO> getClinicalStatusById(
            @PathVariable Long id
    ) {
        ClinicalStatusDTO clinicalStatus = clinicalStatusService.getClinicalStatusById(id);
        return ResponseEntity.ok(clinicalStatus);
    }

    // Atualizar estado clínico
    @PutMapping("/clinical-status/{id}")
    public ResponseEntity<ClinicalStatusDTO> updateClinicalStatus(
            @PathVariable Long id,
            @RequestBody ClinicalStatusDTO dto
    ) {
        ClinicalStatusDTO updatedStatus = clinicalStatusService.updateClinicalStatus(id, dto);
        return ResponseEntity.ok(updatedStatus);
    }

    // Eliminar estado clínico
    @DeleteMapping("/clinical-status/{id}")
    public ResponseEntity<Void> deleteClinicalStatus(
            @PathVariable Long id
    ) {
        clinicalStatusService.deleteClinicalStatus(id);
        return ResponseEntity.noContent().build();
    }
}