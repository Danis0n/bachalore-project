CREATE TABLE types
(
    id          BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    name        VARCHAR(50) UNIQUE           NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE participants
(
    id                BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    name              VARCHAR(255)                 NOT NULL,
    bic               VARCHAR(9) UNIQUE            NOT NULL,
    type_id           BIGINT REFERENCES types (id) NOT NULL,
    registration_date TIMESTAMP                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    email             VARCHAR(100)                 NOT NULL UNIQUE
);

CREATE INDEX idx_participants_type_id ON participants (type_id);

