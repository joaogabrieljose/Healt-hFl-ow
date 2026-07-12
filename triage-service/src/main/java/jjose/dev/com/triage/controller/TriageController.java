package jjose.dev.com.triage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.triage.domain.service.TriageService;
import jjose.dev.com.triage.dto.TriageDTO;

@RestController
@RequestMapping("/triages")
public class TriageController {

    private final TriageService triageService;

    public TriageController(TriageService triageService) {
        this.triageService = triageService;
    }

    // Criar triagem
    @PostMapping
    public ResponseEntity<TriageDTO> createTriage(@RequestBody TriageDTO dto) {
        TriageDTO createdTriage = triageService.createTriage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTriage);
    }

    // Listar todas as triagens
    @GetMapping
    public ResponseEntity<List<TriageDTO>> getAllTriages() {
        List<TriageDTO> triages = triageService.getAllTriages();
        return ResponseEntity.ok(triages);
    }

    // Buscar triagem por ID
    @GetMapping("/{id}")
    public ResponseEntity<TriageDTO> getTriageById(@PathVariable Long id) {
        TriageDTO triage = triageService.getTriageById(id);
        return ResponseEntity.ok(triage);
    }

    // Buscar triagens por paciente
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TriageDTO>> getTriagesByPatientId(@PathVariable Long patientId) {
        List<TriageDTO> triages = triageService.getTriagesByPatientId(patientId);
        return ResponseEntity.ok(triages);
    }

    // Buscar triagem por consulta/agendamento
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<TriageDTO> getTriageByAppointmentId(@PathVariable Long appointmentId) {
        TriageDTO triage = triageService.getTriageByAppointmentId(appointmentId);
        return ResponseEntity.ok(triage);
    }

    // Buscar triagens por nível de urgência
    @GetMapping("/urgency/{urgencyLevel}")
    public ResponseEntity<List<TriageDTO>> getTriagesByUrgencyLevel(@PathVariable String urgencyLevel) {
        List<TriageDTO> triages = triageService.getTriagesByUrgencyLevel(urgencyLevel);
        return ResponseEntity.ok(triages);
    }

    // Atualizar triagem
    @PutMapping("/{id}")
    public ResponseEntity<TriageDTO> updateTriage(
            @PathVariable Long id,
            @RequestBody TriageDTO dto
    ) {
        TriageDTO updatedTriage = triageService.updateTriage(id, dto);
        return ResponseEntity.ok(updatedTriage);
    }

    // Eliminar triagem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTriage(@PathVariable Long id) {
        triageService.deleteTriage(id);
        return ResponseEntity.noContent().build();
    }
}