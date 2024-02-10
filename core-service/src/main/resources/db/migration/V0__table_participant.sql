--Таблица участников
CREATE TABLE participant(
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    bic VARCHAR(9) UNIQUE NOT NULL,
    date_created TIMESTAMP NOT NULL,
    is_active BOOLEAN NOT NULL
);

CREATE INDEX idx_participant_bic ON participant(bic);