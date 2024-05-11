CREATE TABLE role
(
    id          BIGINT      NOT NULL PRIMARY KEY,
    name        VARCHAR(10) NOT NULL UNIQUE,
    description VARCHAR(100)
);

INSERT INTO role (id, name, description)
VALUES (1, 'USER', 'Пользователь платёжной системы'),
       (2, 'ADMIN', 'Администратор платёжной системы');

ALTER TABLE participants
    ADD COLUMN role_id INT NOT NULL REFERENCES role (id) DEFAULT 1;