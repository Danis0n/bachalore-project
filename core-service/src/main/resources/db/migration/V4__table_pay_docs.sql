-- Создание таблицы платежных документов
CREATE TABLE pay_docs
(
    id              BIGSERIAL PRIMARY KEY,
    message_id      BIGINT
        CONSTRAINT fk_pay_docs_message REFERENCES input_message (id),
    debit_acc       VARCHAR(20),
    credit_acc      VARCHAR(20),
    amount          NUMERIC(15, 2)
        CONSTRAINT chk_pay_docs_amount CHECK (amount >= 0),
    step            VARCHAR(255),
    value_date      TIMESTAMP DEFAULT CURRENT_DATE,
    participant_id  BIGINT
        CONSTRAINT fk_pay_docs_participant REFERENCES participant (id),
    start_time      TIMESTAMP,
    finish_time     TIMESTAMP,
    stime_processed TIMESTAMP
);

CREATE INDEX idx_pay_docs_message_id ON pay_docs (message_id);
CREATE INDEX idx_pay_docs_participant_id ON pay_docs (participant_id);