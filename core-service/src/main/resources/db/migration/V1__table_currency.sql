--Таблица валюты
CREATE TABLE currency(
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    code VARCHAR(3) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL
);