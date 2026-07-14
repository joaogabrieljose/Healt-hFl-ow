package jjose.dev.com.scheduling.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jjose.dev.com.scheduling.client.dto.DoctorResponseDTO;

@FeignClient(name = "doctor-service", url = "${services.doctor.url}")
public interface DoctorClient {
    @GetMapping("/doctors/{id}")
    DoctorResponseDTO getDoctorById(@PathVariable Long id);
}