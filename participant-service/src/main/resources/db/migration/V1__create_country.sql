CREATE TABLE countries
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)          NOT NULL,
    code VARCHAR(10) UNIQUE    NOT NULL
);

CREATE INDEX idx_countries_country_code ON countries (code);
