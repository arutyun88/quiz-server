-- Добавление кастомных вопросов

INSERT INTO question (id) VALUES
('11111111-1111-1111-1111-111111111111'),
('22222222-2222-2222-2222-222222222222');

INSERT INTO question_translation (id, question_id, language, text, description) VALUES
('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', 'en', 'What is the capital of France?', 'Some description of the capital of France'),
('44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111', 'ru', 'Столица Франции?', 'Описание столицы Франции'),
('55555555-5555-5555-5555-555555555555', '22222222-2222-2222-2222-222222222222', 'en', 'What is the highest mountain in the world?', 'Some description of the highest mountain'),
('66666666-6666-6666-6666-666666666666', '22222222-2222-2222-2222-222222222222', 'ru', 'Самая высокая гора в мире?', 'Описание самой высокой горы');

INSERT INTO answer (id, question_id, is_correct) VALUES
('77777777-7777-7777-7777-777777777777', '11111111-1111-1111-1111-111111111111', TRUE),
('88888888-8888-8888-8888-888888888888', '11111111-1111-1111-1111-111111111111', FALSE),
('99999999-9999-9999-9999-999999999999', '22222222-2222-2222-2222-222222222222', FALSE),
('00000000-0000-0000-0000-000000000000', '22222222-2222-2222-2222-222222222222', TRUE);

INSERT INTO answer_translation (id, answer_id, language, text) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '77777777-7777-7777-7777-777777777777', 'en', 'Paris'),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '77777777-7777-7777-7777-777777777777', 'ru', 'Париж'),
('cccccccc-cccc-cccc-cccc-cccccccccccc', '88888888-8888-8888-8888-888888888888', 'en', 'Berlin'),
('dddddddd-dddd-dddd-dddd-dddddddddddd', '88888888-8888-8888-8888-888888888888', 'ru', 'Берлин'),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', '99999999-9999-9999-9999-999999999999', 'en', 'K2'),
('ffffffff-ffff-ffff-ffff-ffffffffffff', '99999999-9999-9999-9999-999999999999', 'ru', 'K2'),
('11111111-aaaa-aaaa-aaaa-111111111111', '00000000-0000-0000-0000-000000000000', 'en', 'Everest'),
('22222222-bbbb-bbbb-bbbb-222222222222', '00000000-0000-0000-0000-000000000000', 'ru', 'Эверест');

-- Добавляем новую запись в таблицу question
INSERT INTO question (id) VALUES
('33333333-3333-3333-3333-333333333333'); -- Новый вопрос

-- Переводы для нового вопроса (испанский язык)
INSERT INTO question_translation (id, question_id, language, text, description) VALUES
('44444444-eeee-eeee-eeee-444444444444', '33333333-3333-3333-3333-333333333333', 'es', '¿Cuál es la capital de España?', 'Descripción de la capital de España');

-- Добавляем ответы для нового вопроса
INSERT INTO answer (id, question_id, is_correct) VALUES
('55555555-aaaa-aaaa-aaaa-555555555555', '33333333-3333-3333-3333-333333333333', TRUE), -- Мадрид (правильный ответ)
('66666666-bbbb-bbbb-bbbb-666666666666', '33333333-3333-3333-3333-333333333333', FALSE); -- Барселона (неправильный ответ)

-- Переводы для ответов на испанском языке
INSERT INTO answer_translation (id, answer_id, language, text) VALUES
('77777777-cccc-cccc-cccc-777777777777', '55555555-aaaa-aaaa-aaaa-555555555555', 'es', 'Madrid'), -- Мадрид
('88888888-dddd-dddd-dddd-888888888888', '66666666-bbbb-bbbb-bbbb-666666666666', 'es', 'Barcelona'); -- Барселона
