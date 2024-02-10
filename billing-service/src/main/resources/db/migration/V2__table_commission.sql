--Справочник валюты
CREATE TABLE currency(
   id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
   name VARCHAR(3) UNIQUE NOT NULL,
   description VARCHAR(255)
);

--Справочник типов комиссий
CREATE TABLE type_commission(
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255)
);

-- Таблица комиссий
CREATE TABLE commission (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    currency_id BIGINT REFERENCES currency(id),
    date_activate TIMESTAMP, -- дата начала действия комиссии
    rate NUMERIC(5, 2) NOT NULL, --ставка
    type_id BIGINT REFERENCES type_commission(id)
);

CREATE INDEX idx_commission_currency ON commission(currency_id);
CREATE INDEX idx_commission_type ON commission(type_id);

