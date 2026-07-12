package jjose.dev.com.triage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.triage.domain.service.TriageVitalSignsService;
import jjose.dev.com.triage.dto.TriageVitalSignsDTO;


@RestController
public class TriageVitalSignsController {

    private final TriageVitalSignsService vitalSignsService;

    public TriageVitalSignsController(TriageVitalSignsService vitalSignsService) {
        this.vitalSignsService = vitalSignsService;
    }

    // Criar sinais vitais para uma triagem
    @PostMapping("/triages/{triageId}/vital-signs")
    public ResponseEntity<TriageVitalSignsDTO> createVitalSigns(
            @PathVariable Long triageId,
            @RequestBody TriageVitalSignsDTO dto
    ) {
        TriageVitalSignsDTO createdVitalSigns = vitalSignsService.createVitalSigns(triageId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVitalSigns);
    }

    // Buscar sinais vitais por triageId
    @GetMapping("/triages/{triageId}/vital-signs")
    public ResponseEntity<TriageVitalSignsDTO> getVitalSignsByTriageId(
            @PathVariable Long triageId
    ) {
        TriageVitalSignsDTO vitalSigns = vitalSignsService.getVitalSignsByTriageId(triageId);
        return ResponseEntity.ok(vitalSigns);
    }

    // Buscar sinais vitais pelo ID próprio
    @GetMapping("/vital-signs/{id}")
    public ResponseEntity<TriageVitalSignsDTO> getVitalSignsById(
            @PathVariable Long id
    ) {
        TriageVitalSignsDTO vitalSigns = vitalSignsService.getVitalSignsById(id);
        return ResponseEntity.ok(vitalSigns);
    }

    // Atualizar sinais vitais
    @PutMapping("/vital-signs/{id}")
    public ResponseEntity<TriageVitalSignsDTO> updateVitalSigns(
            @PathVariable Long id,
            @RequestBody TriageVitalSignsDTO dto
    ) {
        TriageVitalSignsDTO updatedVitalSigns = vitalSignsService.updateVitalSigns(id, dto);
        return ResponseEntity.ok(updatedVitalSigns);
    }

    // Eliminar sinais vitais
    @DeleteMapping("/vital-signs/{id}")
    public ResponseEntity<Void> deleteVitalSigns(@PathVariable Long id) {
        vitalSignsService.deleteVitalSigns(id);
        return ResponseEntity.noContent().build();
    }
}