-- Таблица транзакций
CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    uuid UUID NOT NULL,
    sender_participant BIGINT REFERENCES participant(id), -- участник
    amount NUMERIC(12, 2) NOT NULL, -- сумма
    transaction_date TIMESTAMP NOT NULL -- дата транзакции
);

CREATE INDEX idx_sender_participant ON transaction(sender_participant);
