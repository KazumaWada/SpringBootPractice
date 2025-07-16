CREATE TABLE contacts (
    id BIGINT NOT NULL PRIMARY KEY,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    zip_code VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    building_name VARCHAR(255) NOT NULL,
    contact_type VARCHAR(255) NOT NULL,
    body VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP NULL,
    created_at TIMESTAMP NULL
);