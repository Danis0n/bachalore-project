CREATE TABLE outgoing_messages
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    participant_id BIGINT REFERENCES participants (id),
    text           TEXT                  NOT NULL,
    creation_date  TIMESTAMP             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status         VARCHAR(10)           NOT NULL,
    type           VARCHAR(10)           NOT NULL,
    CONSTRAINT check_status CHECK (status IN ('NEW', 'SENT', 'REJECT')),
    CONSTRAINT check_type CHECK (type IN ('PACS008'))
);