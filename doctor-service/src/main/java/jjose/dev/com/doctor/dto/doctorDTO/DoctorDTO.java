package jjose.dev.com.doctor.dto.doctorDTO;

public record DoctorDTO(
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