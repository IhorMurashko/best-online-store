CREATE TABLE users
(
    id                         BIGSERIAL PRIMARY KEY,
    email                      VARCHAR(255) NOT NULL UNIQUE,
    password                   VARCHAR(255) NOT NULL,
    is_account_non_expired     BOOLEAN      NOT NULL DEFAULT true,
    is_account_non_locked      BOOLEAN      NOT NULL DEFAULT true,
    is_credentials_non_expired BOOLEAN      NOT NULL DEFAULT true,
    is_enabled                 BOOLEAN      NOT NULL DEFAULT true,
    created_at                 TIMESTAMP    NOT NULL,
    updated_at                 TIMESTAMP,

    first_name                 VARCHAR(100),
    last_name                  VARCHAR(100),
    phone_number               VARCHAR(20),
    country                    VARCHAR(100),
    city                       VARCHAR(100),
    street_name                VARCHAR(255),
    house_number               VARCHAR(20),
    zip_code                   VARCHAR(20)
);

CREATE TABLE user_roles
(
    user_id BIGINT      NOT NULL,
    roles   VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;
ALTER TABLE users
    ALTER COLUMN id SET DEFAULT nextval('users_seq');


CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_user_roles_user_id ON user_roles (user_id);