package jjose.dev.com.scheduling.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jjose.dev.com.scheduling.client.dto.PatientResponseDTO;

@FeignClient(name = "patient-service",url = "${services.patient.url}")
public interface PatientClient {
    @GetMapping("/patients/{id}")
    PatientResponseDTO getPatientById(@PathVariable Long id);
}