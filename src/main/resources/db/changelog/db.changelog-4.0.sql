--liquibase formatted sql

--changeset eyogo:1
CREATE TABLE password_reset_token
(
    id SERIAL PRIMARY KEY,
    token VARCHAR NOT NULL ,
    user_id INTEGER NOT NULL ,
    expiry_date TIMESTAMP NOT NULL,

    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);
--rollback DROP TABLE password_reset_token;