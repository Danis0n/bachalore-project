CREATE TABLE currency
(
    id          BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    name        VARCHAR(3) UNIQUE            NOT NULL,
    description VARCHAR(255)
);

INSERT INTO currency (name, description)
VALUES ('RUB', 'Russian Ruble'),
       ('USD', 'US Dollar'),
       ('EUR', 'Euro');