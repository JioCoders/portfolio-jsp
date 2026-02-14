-- liquibase formatted sql

-- changeset jiocoders:1
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(20),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(512) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    is_deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMP DEFAULT NULL,
    created_by BIGINT NOT NULL,  -- From BaseAuditEntity
    updated_by BIGINT NOT NULL,  -- From BaseAuditEntity
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id),
    CHECK (role IN ('ADMIN', 'USER'))
);

-- changeset jiocoders:2
INSERT INTO users (name, phone, username, password, email, role, created_by, updated_by) VALUES ('admin', '1234567890', 'admintemp', '$2a$10$vEc.aA6ht5jg1Z2cQK0IQ..Ovze5FVkyVTccJ5039w6o3255NMWbq', 'admintemp@jiocoders.com', 'ADMIN', 1, 1);
INSERT INTO users (name, phone, username, password, email, role, created_by, updated_by) VALUES ('harry', '1234555555', 'usertemp', '$2a$10$c9QLX0h/PBRoz6/QeLuWxOXHQ3QtyZUjH3tWvlcFLhJSEoPoyl/w.', 'usertemp@jiocoders.com', 'USER', 1, 1);
INSERT INTO users (name, phone, username, password, email, role, created_by, updated_by) VALUES ('user1', '1234566666', 'user1', '$2a$10$c9QLX0h/PBRoz6/QeLuWxOXHQ3QtyZUjH3tWvlcFLhJSEoPoyl/w.', 'user1@jiocoders.com', 'USER', 1, 1);
INSERT INTO users (name, phone, username, password, email, role, created_by, updated_by) VALUES ('user2', '1234567777', 'user2', '$2a$10$c9QLX0h/PBRoz6/QeLuWxOXHQ3QtyZUjH3tWvlcFLhJSEoPoyl/w.', 'user2@jiocoders.com', 'USER', 1, 1);
