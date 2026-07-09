package jjose.dev.com.patient.service.dto;

import java.time.LocalDate;

public record PatientDTO(
    Long id,
    
    String fullName,

    LocalDate birthDate,

    String gender,

    String documentNumber,

    String nationality,

   String address
    
) {

}
