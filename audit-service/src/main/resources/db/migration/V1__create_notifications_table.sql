CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,

    type VARCHAR(80) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    recipient VARCHAR(150) NOT NULL,
    subject VARCHAR(200),
    message TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,

    appointment_id BIGINT,
    patient_id BIGINT,
    doctor_id BIGINT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);