package jjose.dev.com.scheduling.client.dto;

public record DoctorResponseDTO(
        Long id,
        String fullName,
        String licenseNumber,
        String email,
        String phone,
        String gender,
        String status,
        Long specialtyId,
        String specialtyName
) {
}