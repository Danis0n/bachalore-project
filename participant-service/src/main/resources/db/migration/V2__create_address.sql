CREATE TABLE addresses
(
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    participant_id BIGINT REFERENCES participants (id),
    city           VARCHAR(50)           NOT NULL,
    street         VARCHAR(50)           NOT NULL,
    house_code     VARCHAR(50)           NOT NULL,
    postal_code    VARCHAR(20)           NOT NULL,
    country_id     BIGINT REFERENCES countries (id)
);

CREATE INDEX idx_addresses_participant_id ON addresses (participant_id);
CREATE INDEX idx_addresses_country_id ON addresses (country_id);
