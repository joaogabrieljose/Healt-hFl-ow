package jjose.dev.com.doctor.controller;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jjose.dev.com.doctor.dto.DoctorScheduleService;
import jjose.dev.com.doctor.dto.doctorScheduleDTO.DoctorScheduleDTO;

@RestController
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    public DoctorScheduleController(DoctorScheduleService doctorScheduleService) {
        this.doctorScheduleService = doctorScheduleService;
    }

    @PostMapping("/doctors/{doctorId}/schedules")
    public ResponseEntity<DoctorScheduleDTO> createSchedule(
            @PathVariable Long doctorId,
            @RequestBody DoctorScheduleDTO dto
    ) {
        DoctorScheduleDTO createdSchedule = doctorScheduleService.createSchedule(doctorId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    @GetMapping("/doctors/{doctorId}/schedules")
    public ResponseEntity<List<DoctorScheduleDTO>> getSchedulesByDoctorId(
            @PathVariable Long doctorId
    ) {
        List<DoctorScheduleDTO> schedules = doctorScheduleService.getSchedulesByDoctorId(doctorId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/doctors/{doctorId}/schedules/available")
    public ResponseEntity<List<DoctorScheduleDTO>> getAvailableSchedulesByDoctorId(
            @PathVariable Long doctorId
    ) {
        List<DoctorScheduleDTO> schedules = doctorScheduleService.getAvailableSchedulesByDoctorId(doctorId);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/doctors/{doctorId}/schedules/day/{dayOfWeek}")
    public ResponseEntity<List<DoctorScheduleDTO>> getSchedulesByDoctorAndDay(
            @PathVariable Long doctorId,
            @PathVariable DayOfWeek dayOfWeek
    ) {
        List<DoctorScheduleDTO> schedules = doctorScheduleService.getSchedulesByDoctorAndDay(doctorId, dayOfWeek);
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<DoctorScheduleDTO> updateSchedule(
            @PathVariable Long id,
            @RequestBody DoctorScheduleDTO dto
    ) {
        DoctorScheduleDTO updatedSchedule = doctorScheduleService.updateSchedule(id, dto);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        doctorScheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}