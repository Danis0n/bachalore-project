ALTER TABLE incoming_messages ADD COLUMN type_id BIGINT REFERENCES types(id);
ALTER TABLE incoming_messages ADD COLUMN reference VARCHAR(50) UNIQUE NOT NULL;
ALTER TABLE incoming_messages ADD COLUMN value_date TIMESTAMP DEFAULT CURRENT_DATE;

ALTER TABLE incoming_messages DROP COLUMN type;

ALTER TABLE pay_docs DROP CONSTRAINT fk_pay_docs_message;

DROP INDEX IF EXISTS idx_pay_docs_message_id;

ALTER TABLE pay_docs ADD CONSTRAINT fk_pay_docs_message FOREIGN KEY (message_id) REFERENCES incoming_messages(id);

CREATE INDEX idx_pay_docs_message_id ON pay_docs (message_id);
CREATE INDEX idx_incoming_messages_on_type_id ON incoming_messages (type_id);
CREATE INDEX idx_incoming_messages_on_reference ON incoming_messages (reference);