package jjose.dev.com.triage.client.dto;

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