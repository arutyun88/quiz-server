-- Для генерации UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Создание таблицы roles
CREATE TABLE roles
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Добавление роли "Owner"
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');
INSERT INTO roles (name)
VALUES ('ROLE_USER');


-- Создание таблицы users
CREATE TABLE users
(
    id       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) UNIQUE  NOT NULL,
    password VARCHAR(255)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL
);

-- Создание промежуточной таблицы user_roles для связи пользователей и ролей
CREATE TABLE user_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- Создание таблицы tokens
CREATE TABLE tokens
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    access_token  VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(255) NOT NULL,
    user_id       UUID         NOT NULL,
    device_id     VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (user_id, device_id)
);

-- Создание таблицы questions
CREATE TABLE questions
(
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    correct_answer CHAR(1)      NOT NULL,
    question       JSONB        NOT NULL
);