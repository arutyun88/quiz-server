-- Добавление кастомных вопросов
WITH inserted_question AS (
    INSERT INTO question (id) VALUES (gen_random_uuid()) RETURNING id
),
inserted_question_translation AS (
    INSERT INTO question_translation (id, question_id, language, text, description)
    SELECT gen_random_uuid(), id, 'en', 'What is the capital of France?', 'Some description of the capital of France' FROM inserted_question
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Столица Франции?', 'Описание столицы Франции' FROM inserted_question
),
inserted_answer_1 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, TRUE FROM inserted_question RETURNING id
),
inserted_answer_1_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Paris' FROM inserted_answer_1
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Париж' FROM inserted_answer_1
),
inserted_answer_2 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question RETURNING id
),
inserted_answer_2_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Berlin' FROM inserted_answer_2
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Берлин' FROM inserted_answer_2
)
SELECT 1;

WITH inserted_question AS (
    INSERT INTO question (id) VALUES (gen_random_uuid()) RETURNING id
),
inserted_question_translation AS (
    INSERT INTO question_translation (id, question_id, language, text, description)
    SELECT gen_random_uuid(), id, 'en', 'What is the highest mountain in the world?', 'Some description of the highest mountain' FROM inserted_question
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Самая высокая гора в мире?', 'Описание самой высокой горы' FROM inserted_question
),
inserted_answer_1 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question RETURNING id
),
inserted_answer_1_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'K2' FROM inserted_answer_1
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'K2' FROM inserted_answer_1
),
inserted_answer_2 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, TRUE FROM inserted_question RETURNING id
),
inserted_answer_2_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Everest' FROM inserted_answer_2
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Эверест' FROM inserted_answer_2
)
SELECT 1;

WITH inserted_question AS (
    INSERT INTO question (id) VALUES (gen_random_uuid()) RETURNING id
),
inserted_question_translation AS (
    INSERT INTO question_translation (id, question_id, language, text, description)
    SELECT gen_random_uuid(), id, 'en', 'What is the capital of Spain?', 'Description of the capital of Spain' FROM inserted_question
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Какая столица Испании?', 'Описание столицы Испании' FROM inserted_question
    UNION ALL
    SELECT gen_random_uuid(), id, 'es', '¿Cuál es la capital de España?', 'Descripción de la capital de España' FROM inserted_question
),
inserted_answer_1 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, TRUE FROM inserted_question RETURNING id
),
inserted_answer_1_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Madrid' FROM inserted_answer_1
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Мадрид' FROM inserted_answer_1
    UNION ALL
    SELECT gen_random_uuid(), id, 'es', 'Madrid' FROM inserted_answer_1
),
inserted_answer_2 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question RETURNING id
),
inserted_answer_2_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Barcelona' FROM inserted_answer_2
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Барселона' FROM inserted_answer_2
    UNION ALL
    SELECT gen_random_uuid(), id, 'es', 'Barcelona' FROM inserted_answer_2
)
SELECT 1;
