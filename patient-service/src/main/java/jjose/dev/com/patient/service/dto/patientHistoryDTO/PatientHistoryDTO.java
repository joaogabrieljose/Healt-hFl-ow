package jjose.dev.com.patient.service.dto;

import java.time.LocalDate;

public record PatientHistoryDTO(

    Long id,

    String eventType,

    String description,

    LocalDate eventDate
) {
    
}
