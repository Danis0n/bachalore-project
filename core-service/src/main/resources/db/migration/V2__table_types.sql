--Таблица-справочник для типов сообщений
CREATE TABLE types(
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL
);