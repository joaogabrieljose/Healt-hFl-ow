CREATE TABLE doctors (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    license_number VARCHAR(80) NOT NULL UNIQUE,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(30),
    gender VARCHAR(20),
    status VARCHAR(30) NOT NULL,
    specialty_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_doctors_specialty
        FOREIGN KEY (specialty_id)
        REFERENCES specialties(id)
        ON DELETE SET NULL
);