CREATE TABLE receipt
(
    id            BIGSERIAL PRIMARY KEY,
    uuid          UUID       NOT NULL UNIQUE,
    value_date    TIMESTAMP  NOT NULL,
    amount        NUMERIC    NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    sender_id     BIGINT     NOT NULL REFERENCES participant (id),
    receiver_id   BIGINT     NOT NULL REFERENCES participant (id),
    status        VARCHAR(8)
        CONSTRAINT receipt_status_type CHECK (status IN ('NEW', 'EFFECTED', 'REJECTED'))
);

CREATE UNIQUE INDEX receipt_idx ON receipt (uuid)
