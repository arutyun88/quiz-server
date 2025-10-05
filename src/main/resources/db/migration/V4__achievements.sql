-- Создание таблицы достижений
CREATE TABLE achievements (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    code VARCHAR(100) UNIQUE NOT NULL,
    points INTEGER NOT NULL DEFAULT 0,
    category VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Создание таблицы локализации достижений
CREATE TABLE achievement_translations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    achievement_id UUID NOT NULL REFERENCES achievements (id) ON DELETE CASCADE,
    language_code VARCHAR(10) NOT NULL, -- 'en', 'ru', 'es', etc.
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (achievement_id, language_code)
);

-- Индексы для быстрого поиска
CREATE INDEX idx_achievement_translations_achievement_id ON achievement_translations (achievement_id);

CREATE INDEX idx_achievement_translations_language ON achievement_translations (language_code);

-- Вставка базовых достижений
INSERT INTO
    achievements (code, points, category)
VALUES
-- Достижения за первые шаги
('FIRST_QUESTION', 10, 'BEGINNER'),
('FIRST_CORRECT', 15, 'BEGINNER'),

-- Достижения за количество вопросов
('QUESTION_MASTER_10', 50, 'PROGRESS'),
('QUESTION_MASTER_50', 150, 'PROGRESS'),
('QUESTION_MASTER_100', 300, 'PROGRESS'),

-- Достижения за точность
('PERFECT_10', 100, 'ACCURACY'),
('PERFECT_50', 250, 'ACCURACY'),

-- Достижения за серии дней
('STREAK_3', 75, 'STREAK'),
('STREAK_7', 200, 'STREAK'),
('STREAK_30', 500, 'STREAK'),

-- Достижения за очки
('POINTS_100', 25, 'POINTS'),
('POINTS_500', 100, 'POINTS'),
('POINTS_1000', 200, 'POINTS'),

-- Достижения за точность ответов
('ACCURACY_80', 150, 'ACCURACY'),
('ACCURACY_90', 300, 'ACCURACY'),
('ACCURACY_95', 500, 'ACCURACY');

-- Вставка английских переводов для существующих достижений
INSERT INTO
    achievement_translations (
        achievement_id,
        language_code,
        name,
        description
    )
SELECT
    a.id,
    'en' as language_code,
    CASE a.code
        WHEN 'FIRST_QUESTION' THEN 'First Question'
        WHEN 'FIRST_CORRECT' THEN 'First Correct Answer'
        WHEN 'QUESTION_MASTER_10' THEN 'Scholar'
        WHEN 'QUESTION_MASTER_50' THEN 'Expert'
        WHEN 'QUESTION_MASTER_100' THEN 'Master'
        WHEN 'PERFECT_10' THEN 'Flawless'
        WHEN 'PERFECT_50' THEN 'Perfection'
        WHEN 'STREAK_3' THEN 'Consistency'
        WHEN 'STREAK_7' THEN 'Dedication'
        WHEN 'STREAK_30' THEN 'Legend'
        WHEN 'POINTS_100' THEN 'Gaining Momentum'
        WHEN 'POINTS_500' THEN 'Points Collector'
        WHEN 'POINTS_1000' THEN 'Points Magnate'
        WHEN 'ACCURACY_80' THEN 'Sharp Shooter'
        WHEN 'ACCURACY_90' THEN 'Sniper'
        WHEN 'ACCURACY_95' THEN 'Flawless'
        ELSE 'Unknown Achievement'
    END as name,
    CASE a.code
        WHEN 'FIRST_QUESTION' THEN 'Answered your first question'
        WHEN 'FIRST_CORRECT' THEN 'Gave your first correct answer'
        WHEN 'QUESTION_MASTER_10' THEN 'Answered 10 questions'
        WHEN 'QUESTION_MASTER_50' THEN 'Answered 50 questions'
        WHEN 'QUESTION_MASTER_100' THEN 'Answered 100 questions'
        WHEN 'PERFECT_10' THEN 'Correctly answered 10 questions in a row'
        WHEN 'PERFECT_50' THEN 'Correctly answered 50 questions in a row'
        WHEN 'STREAK_3' THEN 'Answered questions for 3 consecutive days'
        WHEN 'STREAK_7' THEN 'Answered questions for 7 consecutive days'
        WHEN 'STREAK_30' THEN 'Answered questions for 30 consecutive days'
        WHEN 'POINTS_100' THEN 'Earned 100 points'
        WHEN 'POINTS_500' THEN 'Earned 500 points'
        WHEN 'POINTS_1000' THEN 'Earned 1000 points'
        WHEN 'ACCURACY_80' THEN 'Answer accuracy 80%+ (minimum 10 questions)'
        WHEN 'ACCURACY_90' THEN 'Answer accuracy 90%+ (minimum 20 questions)'
        WHEN 'ACCURACY_95' THEN 'Answer accuracy 95%+ (minimum 50 questions)'
        ELSE 'Unknown achievement description'
    END as description
FROM achievements a;

-- Вставка русских переводов для существующих достижений
INSERT INTO
    achievement_translations (
        achievement_id,
        language_code,
        name,
        description
    )
SELECT
    a.id,
    'ru' as language_code,
    CASE a.code
        WHEN 'FIRST_QUESTION' THEN 'Первый вопрос'
        WHEN 'FIRST_CORRECT' THEN 'Первый правильный ответ'
        WHEN 'QUESTION_MASTER_10' THEN 'Знаток'
        WHEN 'QUESTION_MASTER_50' THEN 'Эксперт'
        WHEN 'QUESTION_MASTER_100' THEN 'Мастер'
        WHEN 'PERFECT_10' THEN 'Безупречность'
        WHEN 'PERFECT_50' THEN 'Совершенство'
        WHEN 'STREAK_3' THEN 'Постоянство'
        WHEN 'STREAK_7' THEN 'Преданность'
        WHEN 'STREAK_30' THEN 'Легенда'
        WHEN 'POINTS_100' THEN 'Набирающий обороты'
        WHEN 'POINTS_500' THEN 'Коллекционер очков'
        WHEN 'POINTS_1000' THEN 'Магнат очков'
        WHEN 'ACCURACY_80' THEN 'Меткий стрелок'
        WHEN 'ACCURACY_90' THEN 'Снайпер'
        WHEN 'ACCURACY_95' THEN 'Безошибочный'
        ELSE 'Неизвестное достижение'
    END as name,
    CASE a.code
        WHEN 'FIRST_QUESTION' THEN 'Ответили на первый вопрос'
        WHEN 'FIRST_CORRECT' THEN 'Дали первый правильный ответ'
        WHEN 'QUESTION_MASTER_10' THEN 'Ответили на 10 вопросов'
        WHEN 'QUESTION_MASTER_50' THEN 'Ответили на 50 вопросов'
        WHEN 'QUESTION_MASTER_100' THEN 'Ответили на 100 вопросов'
        WHEN 'PERFECT_10' THEN 'Правильно ответили на 10 вопросов подряд'
        WHEN 'PERFECT_50' THEN 'Правильно ответили на 50 вопросов подряд'
        WHEN 'STREAK_3' THEN 'Отвечали на вопросы 3 дня подряд'
        WHEN 'STREAK_7' THEN 'Отвечали на вопросы 7 дней подряд'
        WHEN 'STREAK_30' THEN 'Отвечали на вопросы 30 дней подряд'
        WHEN 'POINTS_100' THEN 'Набрали 100 очков'
        WHEN 'POINTS_500' THEN 'Набрали 500 очков'
        WHEN 'POINTS_1000' THEN 'Набрали 1000 очков'
        WHEN 'ACCURACY_80' THEN 'Точность ответов 80%+ (минимум 10 вопросов)'
        WHEN 'ACCURACY_90' THEN 'Точность ответов 90%+ (минимум 20 вопросов)'
        WHEN 'ACCURACY_95' THEN 'Точность ответов 95%+ (минимум 50 вопросов)'
        ELSE 'Описание неизвестного достижения'
    END as description
FROM achievements a;

-- Создание таблицы достижений пользователей
CREATE TABLE user_achievements (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4 (),
    user_id UUID NOT NULL,
    achievement_id UUID NOT NULL,
    unlocked_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (achievement_id) REFERENCES achievements (id) ON DELETE CASCADE,
    UNIQUE (user_id, achievement_id)
);

CREATE INDEX idx_user_achievements_user_id ON user_achievements (user_id);

CREATE INDEX idx_user_achievements_achievement_id ON user_achievements (achievement_id);

-- Дополнительные индексы для оптимизации запросов ачивок
-- Эти индексы улучшат производительность пагинации и сортировки

-- Составной индекс для user_achievements по user_id и unlocked_at
-- Используется для сортировки разблокированных ачивок по дате
CREATE INDEX idx_user_achievements_user_unlocked_at 
ON user_achievements (user_id, unlocked_at DESC);

-- Составной индекс для achievements по category и points
-- Используется для поиска следующего достижения в категории
CREATE INDEX idx_achievements_category_points 
ON achievements (category, points);

-- Составной индекс для achievement_translations по achievement_id и language_code
-- Используется для JOIN с локализацией
CREATE INDEX idx_achievement_translations_achievement_language 
ON achievement_translations (achievement_id, language_code);

-- Индекс для user_achievements по achievement_id и user_id
-- Используется для проверки разблокировки и подсчета
CREATE INDEX idx_user_achievements_achievement_user 
ON user_achievements (achievement_id, user_id);