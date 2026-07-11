package jjose.dev.com.scheduling.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.scheduling.domain.enums.AppointmentStatus;
import jjose.dev.com.scheduling.domain.service.impl.AppointmentService;
import jjose.dev.com.scheduling.dto.appointmentDTO.AppointmentDTO;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Criar consulta/agendamento
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO dto) {
        AppointmentDTO createdAppointment = appointmentService.createAppointment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    // Listar todas as consultas
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Buscar consulta por ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    // Listar consultas de um paciente
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByPatientId(
            @PathVariable Long patientId
    ) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    // Listar consultas de um médico
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctorId(
            @PathVariable Long doctorId
    ) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

    // Listar consultas por data
    @GetMapping("/date/{appointmentDate}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate appointmentDate
    ) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDate(appointmentDate);
        return ResponseEntity.ok(appointments);
    }

    // Listar consultas por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByStatus(
            @PathVariable AppointmentStatus status
    ) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByStatus(status);
        return ResponseEntity.ok(appointments);
    }

    // Atualizar dados da consulta
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentDTO dto
    ) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, dto);
        return ResponseEntity.ok(updatedAppointment);
    }

    // Atualizar apenas o estado da consulta
    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentDTO> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam AppointmentStatus status,
            @RequestParam(required = false) String reason
    ) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointmentStatus(id, status, reason);
        return ResponseEntity.ok(updatedAppointment);
    }

    // Eliminar consulta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}