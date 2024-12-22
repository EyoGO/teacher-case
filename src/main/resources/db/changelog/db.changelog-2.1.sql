--liquibase formatted sql

--changeset eyogo:1
CREATE TABLE IF NOT EXISTS revision
(
    id SERIAL PRIMARY KEY,
    timestamp BIGINT NOT NULL
);

--changeset eyogo:2
CREATE TABLE IF NOT EXISTS users_aud
(
    id BIGINT,
    rev INT REFERENCES revision (id),
    revtype    SMALLINT,
    first_name VARCHAR(124),
    last_name  VARCHAR(124),
    email      VARCHAR(124),
    password   VARCHAR(255),
    role       VARCHAR(255),
    birthday   DATE,
    image      VARCHAR(124),
    gender     VARCHAR(16),
    PRIMARY KEY (rev, id)
);
