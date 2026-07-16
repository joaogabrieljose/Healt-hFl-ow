package jjose.dev.com.triage.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jjose.dev.com.triage.client.dto.AppointmentResponseDTO;

@FeignClient(
        name = "scheduling-service",
        url = "${services.scheduling.url}"
)
public interface SchedulingClient {

    @GetMapping("/appointments/{id}")
    AppointmentResponseDTO getAppointmentById(@PathVariable Long id);
}