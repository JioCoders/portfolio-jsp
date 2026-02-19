-- liquibase formatted sql

-- changeset jio:002-create-groups-table
CREATE TABLE groups (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    creator_id BIGINT NOT NULL,  -- Matches @JoinColumn(name = "creator_id")
    is_deleted BOOLEAN DEFAULT FALSE,
    created_by BIGINT NOT NULL,  -- From BaseAuditEntity
    updated_by BIGINT NOT NULL,  -- From BaseAuditEntity
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_groups_owner FOREIGN KEY (creator_id) REFERENCES users(id),
    CONSTRAINT fk_groups_creator FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_groups_updater FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- changeset jio:003-create-group-members-table
CREATE TABLE group_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(50) DEFAULT 'MEMBER',
    created_by BIGINT NOT NULL,
    updated_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_gm_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    CONSTRAINT fk_gm_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT unique_group_member UNIQUE (group_id, user_id)
);

-- changeset jio:004-create-expenses-table
CREATE TABLE expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT, -- Nullable for 1-on-1 expenses
    title VARCHAR(255) NOT NULL,
    description TEXT,
    total_amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(10) DEFAULT 'INR',
    expense_date DATE NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_by BIGINT NOT NULL,
    updated_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_expenses_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
);
CREATE INDEX idx_expenses_group_date ON expenses(group_id, expense_date);

-- changeset jio:005-create-expense-distributions-table
CREATE TABLE expense_distributions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    expense_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    paid_amount DECIMAL(19, 4) DEFAULT 0,
    share_amount DECIMAL(19, 4) DEFAULT 0,
    CONSTRAINT fk_ed_expense FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE,
    CONSTRAINT fk_ed_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX idx_ed_expense ON expense_distributions(expense_id);
CREATE INDEX idx_ed_user ON expense_distributions(user_id);

-- changeset jio:006-create-settlements-table
CREATE TABLE settlements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT,
    from_user_id BIGINT NOT NULL,
    to_user_id BIGINT NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(10) DEFAULT 'INR',
    created_by BIGINT NOT NULL,
    updated_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    settled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_settle_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    CONSTRAINT fk_settle_from FOREIGN KEY (from_user_id) REFERENCES users(id),
    CONSTRAINT fk_settle_to FOREIGN KEY (to_user_id) REFERENCES users(id)
);

-- changeset jio:007-create-audit-logs-table
CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT,              -- Optional: helps filter activity per group
    entity_type VARCHAR(50) NOT NULL, -- 'GROUP', 'EXPENSE', 'SETTLEMENT', 'MEMBER'
    entity_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,      -- 'CREATED', 'UPDATED', 'DELETED', 'SETTLED'
    created_by BIGINT NOT NULL,  -- From BaseAuditEntity
    updated_by BIGINT NOT NULL,  -- From BaseAuditEntity
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT,                     -- Stores JSON or a human-readable summary
    CONSTRAINT fk_audit_creator FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_audit_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
);
CREATE INDEX idx_audit_group ON audit_logs(group_id);
CREATE INDEX idx_audit_entity ON audit_logs(entity_type, entity_id);
