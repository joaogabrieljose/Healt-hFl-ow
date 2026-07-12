CREATE TABLE triage_vital_signs (
    id BIGSERIAL PRIMARY KEY,

    triage_id BIGINT NOT NULL UNIQUE,

    temperature NUMERIC(4, 1),
    heart_rate INTEGER,
    respiratory_rate INTEGER,
    blood_pressure VARCHAR(30),
    oxygen_saturation INTEGER,
    weight NUMERIC(5, 2),
    height NUMERIC(4, 2),

    measured_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_triage_vital_signs_triage
        FOREIGN KEY (triage_id)
        REFERENCES triages(id)
        ON DELETE CASCADE
);