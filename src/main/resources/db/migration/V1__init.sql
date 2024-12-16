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

-- Создание таблицы question
CREATE TABLE question (
    id UUID PRIMARY KEY
);

-- Создание таблицы question_translation
CREATE TABLE question_translation (
    id UUID PRIMARY KEY,
    question_id UUID REFERENCES question(id) ON DELETE CASCADE,
    language VARCHAR(10) NOT NULL,
    text TEXT NOT NULL,
    description TEXT NOT NULL
);

-- Создание таблицы answer
CREATE TABLE answer (
    id UUID PRIMARY KEY,
    question_id UUID REFERENCES question(id) ON DELETE CASCADE,
    is_correct BOOLEAN NOT NULL
);

-- Создание таблицы answer_translation
CREATE TABLE answer_translation (
    id UUID PRIMARY KEY,
    answer_id UUID REFERENCES answer(id) ON DELETE CASCADE,
    language VARCHAR(10) NOT NULL,
    text TEXT NOT NULL
);

-- Создание таблицы user_question_log
CREATE TABLE user_question_log (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    question_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE
);

-- Создание индекса для ускорения запроса на фильтрацию по user_id и question_id
CREATE INDEX idx_user_question_log_user_id_question_id
ON user_question_log (user_id, question_id);
