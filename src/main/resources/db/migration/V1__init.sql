-- Для генерации UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Установка временной зоны БД
SET TIMEZONE TO 'UTC';

-- Создание функции автоматического обновления поля updated_at
CREATE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создание функции, предотвращающей изменение created_at
CREATE OR REPLACE FUNCTION prevent_update_created_at()
RETURNS TRIGGER AS $$
BEGIN
    IF (OLD.created_at IS DISTINCT FROM NEW.created_at) THEN RAISE EXCEPTION 'Updating "created_at" is not allowed';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создание таблицы roles
CREATE TABLE roles
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

CREATE TRIGGER trigger_create_roles
BEFORE UPDATE ON roles
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

CREATE TRIGGER trigger_update_roles
BEFORE UPDATE ON roles
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- Добавление роли "Owner"
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');
INSERT INTO roles (name)
VALUES ('ROLE_USER');

-- Создание таблицы users
CREATE TABLE users
(
    id       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    password VARCHAR(255)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    name       VARCHAR(255),
    birth_date DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

CREATE TRIGGER trigger_create_users
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

CREATE TRIGGER trigger_update_users
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

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
    UNIQUE (user_id, device_id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER trigger_create_tokens
BEFORE UPDATE ON tokens
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

-- Создание таблицы topic (тема)
CREATE TABLE topic (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

CREATE TRIGGER trigger_create_topic
BEFORE UPDATE ON topic
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

CREATE TRIGGER trigger_update_topic
BEFORE UPDATE ON topic
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- Создание таблицы topic_translation для поддержки мультиязычности тем
CREATE TABLE topic_translation (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    topic_id UUID NOT NULL REFERENCES topic(id) ON DELETE CASCADE,
    language VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    CONSTRAINT unique_topic_language UNIQUE (topic_id, language)
);

CREATE TRIGGER trigger_create_topic_translation
BEFORE UPDATE ON topic_translation
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

CREATE TRIGGER trigger_update_topic_translation
BEFORE UPDATE ON topic_translation
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- Создание таблицы question
CREATE TABLE question (
    id UUID PRIMARY KEY,
    topic_id UUID REFERENCES topic(id) ON DELETE SET NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

CREATE TRIGGER trigger_prevent_update_created_at_question
BEFORE UPDATE ON question
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

CREATE TRIGGER trigger_update_question
BEFORE UPDATE ON question
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- Создание индекса для ускорения запросов по теме
CREATE INDEX idx_question_topic_id ON question (topic_id);

-- Создание таблицы question_translation
CREATE TABLE question_translation (
    id UUID PRIMARY KEY,
    question_id UUID REFERENCES question(id) ON DELETE CASCADE,
    language VARCHAR(10) NOT NULL,
    text TEXT NOT NULL,
    description TEXT NOT NULL,
    hint TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

CREATE TRIGGER trigger_prevent_update_created_at_question_translation
BEFORE UPDATE ON question_translation
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

CREATE TRIGGER trigger_update_question_translation
BEFORE UPDATE ON question_translation
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- Создание таблицы answer
CREATE TABLE answer (
    id UUID PRIMARY KEY,
    question_id UUID REFERENCES question(id) ON DELETE CASCADE,
    is_correct BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

CREATE TRIGGER trigger_prevent_update_created_at_answer
BEFORE UPDATE ON answer
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

CREATE TRIGGER trigger_update_answer
BEFORE UPDATE ON answer
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- Создание таблицы answer_translation
CREATE TABLE answer_translation (
    id UUID PRIMARY KEY,
    answer_id UUID REFERENCES answer(id) ON DELETE CASCADE,
    language VARCHAR(10) NOT NULL,
    text TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NULL
);

CREATE TRIGGER trigger_prevent_update_created_at_answer_translation
BEFORE UPDATE ON answer_translation
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

CREATE TRIGGER trigger_update_answer_translation
BEFORE UPDATE ON answer_translation
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

-- Создание таблицы user_question_log
CREATE TABLE user_question_log (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    question_id UUID NOT NULL,
    answer_id UUID,
    is_correct BOOLEAN,
    answered_at TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE,
    FOREIGN KEY (answer_id) REFERENCES answer (id) ON DELETE SET NULL,
    CONSTRAINT unique_user_question UNIQUE (user_id, question_id)
);

CREATE TRIGGER trigger_prevent_update_created_at_user_question_log
BEFORE UPDATE ON user_question_log
FOR EACH ROW
EXECUTE FUNCTION prevent_update_created_at();

-- Создание индекса для ускорения запроса на фильтрацию по user_id и question_id
CREATE INDEX idx_user_question_log_user_id_question_id
ON user_question_log (user_id, question_id);

-- Создание индекса для ускорения запроса на фильтрацию по user_id и is_correct
CREATE INDEX idx_user_question_log_is_correct
ON user_question_log (user_id, is_correct);
