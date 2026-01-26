-- liquibase formatted sql

-- changeset jiocoders:1
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(512) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- changeset jiocoders:2
INSERT INTO users (username, password, email, role) VALUES ('admintemp', '$2a$10$vEc.aA6ht5jg1Z2cQK0IQ..Ovze5FVkyVTccJ5039w6o3255NMWbq', 'admintemp@jiocoders.com', 'ADMIN');
INSERT INTO users (username, password, email, role) VALUES ('usertemp', '$2a$10$c9QLX0h/PBRoz6/QeLuWxOXHQ3QtyZUjH3tWvlcFLhJSEoPoyl/w.', 'usertemp@jiocoders.com', 'USER');
