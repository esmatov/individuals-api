CREATE TABLE person.users
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    address_id UUID REFERENCES person.addresses (id),
    email      VARCHAR(64),
    secret_key VARCHAR(32),
    first_name VARCHAR(32),
    last_name  VARCHAR(32),
    created    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    filled     BOOLEAN          DEFAULT FALSE
);
