-- liquibase formatted sql

-- changeset jiocoders:1
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- changeset jiocoders:2
INSERT INTO users (username, password, email, role) VALUES ('admin', 'admin123', 'admin@jiocoders.com', 'ADMIN');
INSERT INTO users (username, password, email, role) VALUES ('user', 'user123', 'user@jiocoders.com', 'USER');
