CREATE TABLE person.addresses
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    country_id INTEGER REFERENCES person.countries (id),
    created    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    address    VARCHAR(128),
    zip_code   VARCHAR(16),
    city       VARCHAR(16),
    state      VARCHAR(32),
    archived   BOOLEAN          DEFAULT FALSE
);
