-- Для генерации UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


-- Создание таблицы users
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL -- Поле для хранения роли, связанное с Enum Role
);

-- Добавление индекса для поля role
CREATE INDEX idx_users_role ON users (role);