CREATE SEQUENCE IF NOT EXISTS users_seq START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS users
(
    id                         BIGSERIAL PRIMARY KEY,
    email                      VARCHAR(45) NOT NULL UNIQUE,
    password                   VARCHAR(100),
    is_account_non_expired     BOOLEAN     NOT NULL DEFAULT true,
    is_account_non_locked      BOOLEAN     NOT NULL DEFAULT true,
    is_credentials_non_expired BOOLEAN     NOT NULL DEFAULT true,
    is_enabled                 BOOLEAN     NOT NULL DEFAULT true,
    created_at                 TIMESTAMP   NOT NULL,
    updated_at                 TIMESTAMP,
    first_name                 VARCHAR(30),
    last_name                  VARCHAR(30),
    phone_number               VARCHAR(20),
    country                    VARCHAR(40),
    city                       VARCHAR(40),
    street_name                VARCHAR(100),
    house_number               VARCHAR(20),
    zip_code                   VARCHAR(20),
    auth_provider              VARCHAR(20),
    oauth_id                   VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT      NOT NULL,
    roles   VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS billing_info
(
    id           BIGSERIAL PRIMARY KEY,
    name_on_card VARCHAR(255) NOT NULL,
    card_number  VARCHAR(255) NOT NULL,
    expire_date  VARCHAR(5)   NOT NULL,
    user_id      BIGINT       NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_user_roles_user_id ON user_roles (user_id);
CREATE INDEX idx_billing_info_user_id ON billing_info (user_id);
