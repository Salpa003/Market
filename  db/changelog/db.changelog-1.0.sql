--liquibase formatted sql

--changeset sapla:1
CREATE DATABASE market;

--changeset salpa:2
CREATE SCHEMA users;

--changeset salpa:3
CREATE TABLE users.users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(32),
    password VARCHAR(64),
    amount   DOUBLE PRECISION,
    avatar   VARCHAR(32)
);