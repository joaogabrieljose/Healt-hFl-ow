CREATE TABLE patient_history (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    event_type VARCHAR(50),
    description TEXT,
    event_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_patient_history_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients(id)
        ON DELETE CASCADE
);