CREATE TABLE participant_credentials
(
    id              BIGSERIAL    NOT NULL PRIMARY KEY,
    participant_id  BIGINT       NOT NULL REFERENCES participants (id),
    login           VARCHAR(16)  NOT NULL UNIQUE,
    hashed_password VARCHAR(255) NOT NULL
);

CREATE INDEX idx_participant_cred_id ON participant_credentials (participant_id);
CREATE INDEX idx_participants_cred_login ON participant_credentials (login);