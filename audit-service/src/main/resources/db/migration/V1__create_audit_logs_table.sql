CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,

    event_type VARCHAR(100) NOT NULL,
    source_service VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id BIGINT,

    description TEXT NOT NULL,

    appointment_id BIGINT,
    patient_id BIGINT,
    doctor_id BIGINT,

    previous_status VARCHAR(50),
    new_status VARCHAR(50),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);