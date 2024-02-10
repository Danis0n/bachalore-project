--Таблица для отправки данных о транзакции в другие сервисы
CREATE TABLE transfer_outbox(
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    bic_cd VARCHAR(9) NOT NULL,
    bic_db VARCHAR(9) NOT NULL,
    value_date TIMESTAMP NOT NULL,
    amount NUMERIC(15, 2),
    currency_code VARCHAR(3) NOT NULL,
    uuid UUID UNIQUE NOT NULL
);

CREATE INDEX idx_transfer_outbox_value_date ON transfer_outbox(value_date);