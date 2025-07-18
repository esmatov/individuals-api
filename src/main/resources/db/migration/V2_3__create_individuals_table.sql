CREATE TABLE person.individuals
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id         UUID UNIQUE REFERENCES person.users (id),
    passport_number VARCHAR(32),
    phone_number    VARCHAR(16),
    email           VARCHAR(64),
    verified_at     TIMESTAMP NOT NULL,
    archived_at     TIMESTAMP NOT NULL,
    status          VARCHAR(8)
);
