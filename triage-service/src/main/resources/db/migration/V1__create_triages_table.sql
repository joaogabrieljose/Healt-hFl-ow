CREATE TABLE triages (
    id BIGSERIAL PRIMARY KEY,

    patient_id BIGINT NOT NULL,
    appointment_id BIGINT NOT NULL,

    main_complaint TEXT NOT NULL,
    symptoms TEXT,

    urgency_level VARCHAR(30) NOT NULL,

    notes TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);