ALTER TABLE transaction ADD COLUMN receiver_participant BIGINT REFERENCES participant(id);
ALTER TABLE transaction ADD COLUMN currency BIGINT REFERENCES currency(id);
ALTER TABLE transaction ADD COLUMN status VARCHAR(8) CONSTRAINT status_name CHECK(status IN ('SUCCESS', 'FAIL'));