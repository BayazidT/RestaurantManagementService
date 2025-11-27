CREATE TABLE rms.roles (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           name VARCHAR(50) NOT NULL UNIQUE,
                           description TEXT NULL,
                           is_active BOOLEAN NOT NULL DEFAULT TRUE,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Default roles
INSERT INTO rms.roles (name, description) VALUES
                                              ('ADMIN', 'Full system access'),
                                              ('MANAGER', 'Manage restaurant operations'),
                                              ('STAFF', 'Waiters, cooks, etc.'),
                                              ('CUSTOMER', 'End-user customer');


CREATE TABLE rms.permissions (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 name VARCHAR(100) NOT NULL UNIQUE,
                                 description TEXT NULL,
                                 module VARCHAR(50) NULL, -- e.g., USER, ORDER, MENU
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Example permissions
INSERT INTO rms.permissions (name, description, module) VALUES
                                                            ('user:create', 'Create new users', 'USER'),
                                                            ('user:read', 'View user details', 'USER'),
                                                            ('user:update', 'Update user info', 'USER'),
                                                            ('user:delete', 'Soft-delete users', 'USER'),
                                                            ('order:manage', 'Create/edit orders', 'ORDER'),
                                                            ('menu:manage', 'Manage menu items', 'MENU');



CREATE TABLE rms.role_permissions (
                                      role_id UUID NOT NULL REFERENCES rms.roles(id) ON DELETE CASCADE,
                                      permission_id UUID NOT NULL REFERENCES rms.permissions(id) ON DELETE CASCADE,
                                      PRIMARY KEY (role_id, permission_id),
                                      granted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE rms.user_roles (
                                user_id UUID NOT NULL REFERENCES rms.users(id) ON DELETE CASCADE,
                                role_id UUID NOT NULL REFERENCES rms.roles(id) ON DELETE CASCADE,
                                assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                assigned_by UUID NULL REFERENCES rms.users(id),
                                PRIMARY KEY (user_id, role_id)
);

CREATE TABLE rms.refresh_tokens (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    user_id UUID NOT NULL REFERENCES rms.users(id) ON DELETE CASCADE,
                                    token VARCHAR NOT NULL UNIQUE,
                                    expires_at TIMESTAMP NOT NULL,
                                    revoked_at TIMESTAMP NULL,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    created_from_ip VARCHAR(45) NULL,
                                    created_from_user_agent TEXT NULL
);

CREATE INDEX idx_refresh_token ON rms.refresh_tokens(token);
CREATE INDEX idx_refresh_user ON rms.refresh_tokens(user_id);



CREATE TABLE rms.audit_logs (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                user_id UUID NULL REFERENCES rms.users(id),
                                action VARCHAR(100) NOT NULL, -- e.g., USER_LOGIN, USER_DELETED
                                entity_type VARCHAR(50) NULL, -- e.g., USER, ORDER
                                entity_id UUID NULL,
                                old_values JSONB NULL,
                                new_values JSONB NULL,
                                ip_address VARCHAR(45) NULL,
                                user_agent TEXT NULL,
                                occurred_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_user ON rms.audit_logs(user_id);
CREATE INDEX idx_audit_action ON rms.audit_logs(action);
CREATE INDEX idx_audit_entity ON rms.audit_logs(entity_type, entity_id);

