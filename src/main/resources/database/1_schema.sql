CREATE TABLE IF NOT EXISTS tb_application_secret_authority (
    client_id VARCHAR(255) PRIMARY KEY,
    cd_authority VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS tb_address (
    id       SERIAL PRIMARY KEY,
    street   VARCHAR(50) NULL,
    city     VARCHAR(50) NULL,
    state    VARCHAR(50) NULL,
    postal_code VARCHAR(50) NULL,
    dh_insert TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS tb_client (
    id         SERIAL PRIMARY KEY,
    password   VARCHAR(500) NOT NULL,
    email      VARCHAR(50) NULL,
    cpf        VARCHAR(50) NULL UNIQUE,
    name       VARCHAR(50) NOT NULL,
    id_address INTEGER REFERENCES tb_address(id),
    dh_insert TIMESTAMP DEFAULT NOW()
);