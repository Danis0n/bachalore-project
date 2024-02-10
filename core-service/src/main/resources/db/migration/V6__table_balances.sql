-- Таблица баланса
CREATE TABLE balances(
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    account_id BIGINT CONSTRAINT fk_balances_account REFERENCES account(Id),
    credit NUMERIC(15, 2),
    debit NUMERIC(15, 2),
    doc_debits BIGINT,
    doc_credits BIGINT,
    CONSTRAINT chk_balances_credit_debit CHECK (credit >= 0 AND debit >= 0),
    CONSTRAINT chk_balances_doc_debits_credits CHECK (doc_debits >= 0 AND doc_credits >= 0)
);

CREATE INDEX idx_balances_account_id ON balances(account_id);