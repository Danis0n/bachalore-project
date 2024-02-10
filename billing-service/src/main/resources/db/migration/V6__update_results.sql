ALTER TABLE results ADD COLUMN status VARCHAR(10) CONSTRAINT result_status CHECK(status IN ('PROCESSED', 'EXCLUDE', 'NONE'));
ALTER TABLE results RENAME COLUMN commission_id TO descriptor_id;
ALTER TABLE results ADD COLUMN receiver_participant BIGINT REFERENCES participant(id);
ALTER TABLE results ADD COLUMN sender_participant BIGINT REFERENCES participant(id);