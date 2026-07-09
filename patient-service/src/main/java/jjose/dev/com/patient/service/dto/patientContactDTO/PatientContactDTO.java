package jjose.dev.com.patient.service.dto.patientContactDTO;

public record PatientContactDTO(
    Long id,

    String phone,

    String email,

    String emergencyContactName,

    String emergencyContactPhone,

    String relationship
) {
    
}
