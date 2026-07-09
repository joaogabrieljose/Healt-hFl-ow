package jjose.dev.com.patient.service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.patient.service.dto.PatientHistoryService;
import jjose.dev.com.patient.service.dto.patientHistoryDTO.PatientHistoryDTO;

@RestController
public class PatientHistoryController {

    private final PatientHistoryService patientHistoryService;

    public PatientHistoryController(PatientHistoryService patientHistoryService) {
        this.patientHistoryService = patientHistoryService;
    }

    // Criar histórico para um paciente
    @PostMapping("/patients/{patientId}/histories")
    public ResponseEntity<PatientHistoryDTO> createHistory(
            @PathVariable Long patientId,
            @RequestBody PatientHistoryDTO dto
    ) {
        PatientHistoryDTO createdHistory = patientHistoryService.createHistory(patientId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHistory);
    }

    // Listar históricos de um paciente
    @GetMapping("/patients/{patientId}/histories")
    public ResponseEntity<List<PatientHistoryDTO>> getHistoriesByPatientId(
            @PathVariable Long patientId
    ) {
        List<PatientHistoryDTO> histories = patientHistoryService.getHistoriesByPatientId(patientId);
        return ResponseEntity.ok(histories);
    }

    // Buscar histórico pelo ID
    @GetMapping("/histories/{id}")
    public ResponseEntity<PatientHistoryDTO> getHistoryById(@PathVariable Long id) {
        PatientHistoryDTO history = patientHistoryService.getHistoryById(id);
        return ResponseEntity.ok(history);
    }

    // Atualizar histórico
    @PutMapping("/histories/{id}")
    public ResponseEntity<PatientHistoryDTO> updateHistory(
            @PathVariable Long id,
            @RequestBody PatientHistoryDTO dto
    ) {
        PatientHistoryDTO updatedHistory = patientHistoryService.updateHistory(id, dto);
        return ResponseEntity.ok(updatedHistory);
    }

    // Eliminar histórico
    @DeleteMapping("/histories/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        patientHistoryService.deleteHistory(id);
        return ResponseEntity.noContent().build();
    }
}