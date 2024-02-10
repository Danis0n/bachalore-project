--Таблица участников
CREATE TABLE participant(
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    bic VARCHAR(9) UNIQUE NOT NULL,
    type VARCHAR(50)
);

CREATE INDEX idx_bic ON participant(bic);