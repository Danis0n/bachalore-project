CREATE TABLE transaction (
   id BIGSERIAL PRIMARY KEY NOT NULL,
   status VARCHAR(1),
   value_date TIMESTAMP DEFAULT CURRENT_DATE,
   amount NUMERIC(15, 2) CONSTRAINT chk_transaction_amount CHECK (amount >= 0),
   error VARCHAR(255),
   pay_doc_id BIGINT REFERENCES pay_docs(id),
   CONSTRAINT check_transaction_status CHECK (status IN ('R', 'E', 'Q', 'F', 'X'))
);
