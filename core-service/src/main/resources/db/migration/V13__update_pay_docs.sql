ALTER TABLE pay_docs ADD COLUMN guid UUID;
ALTER TABLE pay_docs ADD COLUMN currency_id BIGINT REFERENCES currency(id);
ALTER TABLE pay_docs ADD CONSTRAINT step_state CHECK(step IN ('F', 'E', 'D', 'A', 'P'));
