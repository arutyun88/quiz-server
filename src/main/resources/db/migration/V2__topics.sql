-- Создание тем
WITH
science_topic AS (
    INSERT INTO topic DEFAULT VALUES RETURNING id
),
science_topic_translation_en AS (
    INSERT INTO topic_translation (topic_id, language, name, description)
    SELECT id, 'en', 'Science', 'Questions about scientific concepts and discoveries'
    FROM science_topic
),
science_topic_translation_ru AS (
    INSERT INTO topic_translation (topic_id, language, name, description)
    SELECT id, 'ru', 'Наука', 'Вопросы о научных концепциях и открытиях'
    FROM science_topic
),
geography_topic AS (
    INSERT INTO topic DEFAULT VALUES RETURNING id
),
geography_topic_translation_en AS (
    INSERT INTO topic_translation (topic_id, language, name, description)
    SELECT id, 'en', 'Geography', 'Questions about world geography'
    FROM geography_topic
),
geography_topic_translation_ru AS (
    INSERT INTO topic_translation (topic_id, language, name, description)
    SELECT id, 'ru', 'География', 'Вопросы о мировой географии'
    FROM geography_topic
),
history_topic AS (
    INSERT INTO topic DEFAULT VALUES RETURNING id
),
history_topic_translation_en AS (
    INSERT INTO topic_translation (topic_id, language, name, description)
    SELECT id, 'en', 'History', 'Questions about historical events and figures'
    FROM history_topic
),
history_topic_translation_ru AS (
    INSERT INTO topic_translation (topic_id, language, name, description)
    SELECT id, 'ru', 'История', 'Вопросы об исторических событиях и личностях'
    FROM history_topic
),
art_topic AS (
    INSERT INTO topic DEFAULT VALUES RETURNING id
),
art_topic_translation_en AS (
    INSERT INTO topic_translation (topic_id, language, name, description)
    SELECT id, 'en', 'Art', 'Questions about art and famous artists'
    FROM art_topic
),
art_topic_translation_ru AS (
    INSERT INTO topic_translation (topic_id, language, name, description)
    SELECT id, 'ru', 'Искусство', 'Вопросы об искусстве и известных художниках'
    FROM art_topic
)
SELECT 1;