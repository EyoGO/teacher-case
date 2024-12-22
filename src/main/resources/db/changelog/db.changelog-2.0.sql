--liquibase formatted sql

--changeset eyogo:1
ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP;

--changeset eyogo:2
ALTER TABLE users
    ADD COLUMN modified_at TIMESTAMP;

--changeset eyogo:3
ALTER TABLE users
    ADD COLUMN created_by VARCHAR(64);

--changeset eyogo:4
ALTER TABLE users
    ADD COLUMN last_modified_by VARCHAR(64);
