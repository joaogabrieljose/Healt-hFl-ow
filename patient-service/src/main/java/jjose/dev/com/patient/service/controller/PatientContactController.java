package jjose.dev.com.patient.service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.patient.service.dto.PatientContactService;
import jjose.dev.com.patient.service.dto.patientContactDTO.PatientContactDTO;

@RestController
public class PatientContactController {

    private final PatientContactService patientContactService;

    public PatientContactController(PatientContactService patientContactService) {
        this.patientContactService = patientContactService;
    }

    // Criar contacto para um paciente
    @PostMapping("/patients/{patientId}/contacts")
    public ResponseEntity<PatientContactDTO> createContact(
            @PathVariable Long patientId,
            @RequestBody PatientContactDTO dto
    ) {
        PatientContactDTO createdContact = patientContactService.createContact(patientId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    // Listar todos os contactos de um paciente
    @GetMapping("/patients/{patientId}/contacts")
    public ResponseEntity<List<PatientContactDTO>> getContactsByPatientId(
            @PathVariable Long patientId
    ) {
        List<PatientContactDTO> contacts = patientContactService.getContactsByPatientId(patientId);
        return ResponseEntity.ok(contacts);
    }

    // Buscar um contacto pelo ID
    @GetMapping("/contacts/{id}")
    public ResponseEntity<PatientContactDTO> getContactById(@PathVariable Long id) {
        PatientContactDTO contact = patientContactService.getContactById(id);
        return ResponseEntity.ok(contact);
    }

    // Atualizar contacto
    @PutMapping("/contacts/{id}")
    public ResponseEntity<PatientContactDTO> updateContact(
            @PathVariable Long id,
            @RequestBody PatientContactDTO dto
    ) {
        PatientContactDTO updatedContact = patientContactService.updateContact(id, dto);
        return ResponseEntity.ok(updatedContact);
    }

    // Eliminar contacto
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        patientContactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}