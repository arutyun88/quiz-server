-- Создание таблицы user_question_logs
CREATE TABLE user_question_logs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    question_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions (id) ON DELETE CASCADE
);

-- Создание индекса для ускорения запроса на фильтрацию по user_id и question_id
CREATE INDEX idx_user_question_logs_user_id_question_id
ON user_question_logs (user_id, question_id);
