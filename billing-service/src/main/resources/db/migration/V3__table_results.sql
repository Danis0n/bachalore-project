-- Таблица результатов
CREATE TABLE results (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    transaction_id BIGINT REFERENCES transaction(id),
    commission_id BIGINT REFERENCES commission(id),
    participant_id BIGINT REFERENCES participant(id),
    commission_amount NUMERIC(12, 2) -- сумма комиссии
);