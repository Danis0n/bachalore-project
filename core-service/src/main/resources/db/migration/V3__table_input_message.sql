--Таблица входящих сообщений
CREATE TABLE input_message (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    message_text TEXT NOT NULL,
    type_id BIGINT CONSTRAINT fk_input_message_type REFERENCES types(id) NOT NULL,
    participant_id BIGINT CONSTRAINT fk_input_message_participant REFERENCES participant(id),
    reference VARCHAR(50) UNIQUE NOT NULL,
    value_date TIMESTAMP DEFAULT CURRENT_DATE,
    description VARCHAR(255)
);

CREATE INDEX idx_input_message_participant_id ON input_message (participant_id);