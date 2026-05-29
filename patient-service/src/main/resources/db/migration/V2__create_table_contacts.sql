
CREATE TABLE patient_contacts (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    phone VARCHAR(30),
    email VARCHAR(120),
    emergency_contact_name VARCHAR(150),
    emergency_contact_phone VARCHAR(30),
    relationship VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_patient_contacts_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients(id)
        ON DELETE CASCADE
);