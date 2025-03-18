--liquibase formatted sql

--changeset eyogo:1
CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(124) NOT NULL,
    last_name VARCHAR(124) NOT NULL,
    email    VARCHAR(124) NOT NULL UNIQUE,
    password VARCHAR(32)  NOT NULL,
    role     VARCHAR(32)  NOT NULL,
    birthday DATE NOT NULL,
    image    VARCHAR(124),
    gender   VARCHAR(16)  NOT NULL,

    UNIQUE  (first_name, last_name)
);
--rollback DROP TABLE users;

--changeset eyogo:2
CREATE TABLE unit
(
    id SERIAL PRIMARY KEY,
    unit_name VARCHAR NOT NULL,
    parent_id INTEGER NOT NULL,
    managed_by_admin BOOLEAN NOT NULL DEFAULT false,

    UNIQUE  (unit_name, parent_id)
);
--rollback DROP TABLE unit;

--changeset eyogo:3
CREATE TABLE activity
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL ,
    unit_id INTEGER NOT NULL,
    activity_name VARCHAR NOT NULL,
    description VARCHAR,
    author_id INTEGER NOT NULL,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_unit_id FOREIGN KEY (unit_id) REFERENCES unit(id),
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users(id),

    UNIQUE (user_id, unit_id, activity_name)
);
--rollback DROP TABLE activity;
