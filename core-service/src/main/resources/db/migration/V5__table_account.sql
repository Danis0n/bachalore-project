-- Таблица счета
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100),
    accType VARCHAR(20) DEFAULT 'cash',
    currency_id BIGINT CONSTRAINT fk_account_currency REFERENCES currency(id),
    is_active BOOLEAN,
    participant_id BIGINT CONSTRAINT fk_account_participant REFERENCES participant(id),
    open_date TIMESTAMP,
    close_date TIMESTAMP
);

CREATE INDEX idx_account_participant_id ON account (participant_id);