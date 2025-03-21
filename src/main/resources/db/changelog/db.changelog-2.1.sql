--liquibase formatted sql

--changeset eyogo:1
CREATE TABLE IF NOT EXISTS revision
(
    id        SERIAL PRIMARY KEY,
    timestamp BIGINT NOT NULL
);

--changeset eyogo:2
CREATE TABLE IF NOT EXISTS users_aud
(
    id         BIGINT,
    rev        INT REFERENCES revision (id),
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

--changeset eyogo:3
CREATE TABLE IF NOT EXISTS unit_aud
(
    id               BIGINT,
    rev              INT REFERENCES revision (id),
    revtype          SMALLINT,
    unit_name        VARCHAR,
    parent_id        INTEGER,
    managed_by_admin BOOLEAN,
    PRIMARY KEY (rev, id)
);

--changeset eyogo:4
CREATE TABLE IF NOT EXISTS activity_aud
(
    id            BIGINT,
    rev           INT REFERENCES revision (id),
    revtype       SMALLINT,
    user_id       INTEGER,
    unit_id       INTEGER,
    activity_name VARCHAR,
    description   VARCHAR,
    author_id     INTEGER,
    PRIMARY KEY (rev, id)
);
