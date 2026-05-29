CREATE TABLE clinical_status (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL UNIQUE,
    blood_type VARCHAR(10),
    allergies TEXT,
    chronic_diseases TEXT,
    current_medications TEXT,
    clinical_notes TEXT,
    status VARCHAR(30),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_clinical_status_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients(id)
        ON DELETE CASCADE
);