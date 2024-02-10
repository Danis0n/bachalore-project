--Таблица участников
CREATE TABLE participant
(
    id    BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    bic   VARCHAR(9) UNIQUE            NOT NULL,
    name  VARCHAR(255)                 NOT NULL,
    type  VARCHAR(50),
    email VARCHAR(100)                 NOT NULL UNIQUE
);

CREATE INDEX idx_bic ON participant (bic);