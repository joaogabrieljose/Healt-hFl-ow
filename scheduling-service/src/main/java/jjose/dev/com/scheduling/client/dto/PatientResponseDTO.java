package jjose.dev.com.scheduling.client.dto;

import java.time.LocalDate;

public record PatientResponseDTO(
        Long id,
        String fullName,
        LocalDate birthDate,
        String gender,
        String documentNumber,
        String nationality,
        String address
) {
}