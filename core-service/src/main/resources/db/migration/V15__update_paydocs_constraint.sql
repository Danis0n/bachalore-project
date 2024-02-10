ALTER TABLE pay_docs
ALTER COLUMN stime_processed TYPE NUMERIC USING (EXTRACT(EPOCH FROM stime_processed)::numeric);
