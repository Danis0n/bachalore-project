ALTER TABLE commission RENAME TO descriptor;

ALTER TABLE descriptor ADD COLUMN amount_before NUMERIC(15, 2);
ALTER TABLE descriptor ADD COLUMN amount_after NUMERIC(15, 2);
ALTER TABLE descriptor ADD COLUMN min_percent NUMERIC(3, 1);
ALTER TABLE descriptor ADD COLUMN max_percent NUMERIC(3, 1);
ALTER TABLE descriptor DROP COLUMN type_id;
ALTER TABLE descriptor ADD COLUMN type VARCHAR(8) CONSTRAINT descriptor_type CHECK (type IN ('ABSOLUTE', 'PERCENT'));