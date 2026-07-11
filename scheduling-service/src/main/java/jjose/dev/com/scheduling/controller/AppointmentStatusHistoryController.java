package jjose.dev.com.scheduling.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.scheduling.domain.service.impl.AppointmentStatusHistoryService;
import jjose.dev.com.scheduling.dto.appointmentStatusHistoryDTO.AppointmentStatusHistoryDTO;

@RestController
@RequestMapping("/appointment-history")
public class AppointmentStatusHistoryController {

    private final AppointmentStatusHistoryService historyService;

    public AppointmentStatusHistoryController(AppointmentStatusHistoryService historyService) {
        this.historyService = historyService;
    }

    // Listar histórico de uma consulta
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<AppointmentStatusHistoryDTO>> getHistoryByAppointmentId(
            @PathVariable Long appointmentId
    ) {
        List<AppointmentStatusHistoryDTO> history = historyService.getHistoryByAppointmentId(appointmentId);
        return ResponseEntity.ok(history);
    }

    // Buscar um registo de histórico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentStatusHistoryDTO> getHistoryById(
            @PathVariable Long id
    ) {
        AppointmentStatusHistoryDTO history = historyService.getHistoryById(id);
        return ResponseEntity.ok(history);
    }
}