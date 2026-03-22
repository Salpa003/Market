--liquibase formatted sql

--changeset salpa:1
CREATE SCHEMA users;
--changeset salpa:2
CREATE TABLE users.users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(32),
    login    VARCHAR(32) UNIQUE,
    password VARCHAR(64),
    amount   DOUBLE PRECISION,
    avatar   VARCHAR(32),
    role     VARCHAR(10)
);
--changeset salpa:3
CREATE SCHEMA skin;
--changeset salpa:4
CREATE TABLE skin.collection
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(64) UNIQUE NOT NULL,
    image VARCHAR(64)
);
--changeset salpa:5
CREATE TABLE skin.skin
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(32) UNIQUE,
    image         VARCHAR(32),
    gun           VARCHAR(32),
    rarely        VARCHAR(16),
    collection_id INT REFERENCES skin.collection (id) NOT NULL
);

--changeset salpa:6
CREATE TABLE users.news
(
    id          SERIAL PRIMARY KEY,
    header      VARCHAR(32) UNIQUE,
    description TEXT,
    image       VARCHAR(16)
);
