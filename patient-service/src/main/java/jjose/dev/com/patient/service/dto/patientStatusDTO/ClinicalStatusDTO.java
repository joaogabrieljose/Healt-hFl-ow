package jjose.dev.com.patient.service.dto;

public record ClinicalStatusDTO(
    Long id,
    
    String bloodType,

    String allergies,

    String chronicDiseases,

    String currentMedications,

    String clinicalNotes,

    String status
) {
    
}
