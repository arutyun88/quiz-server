-- Добавление первичной коллекции вопросов
-- Вопрос 1: Наука
WITH
science_topic AS (
    SELECT id FROM topic WHERE id IN (SELECT topic_id FROM topic_translation WHERE name = 'Science' OR name = 'Наука' LIMIT 1)
),
inserted_question AS (
    INSERT INTO question (id, topic_id)
    VALUES (uuid_generate_v4(), (SELECT id FROM science_topic))
    RETURNING id
),
inserted_question_translation AS (
    INSERT INTO question_translation (id, question_id, language, text, description, hint)
    SELECT gen_random_uuid(), id, 'en',
        'What is the chemical symbol for water?',
        'Water is composed of two hydrogen atoms and one oxygen atom, making its chemical formula H2O.',
        'Think about the chemical formula of water that you use daily.'
    FROM inserted_question
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru',
        'Какой химический символ воды?',
        'Вода состоит из двух атомов водорода и одного атома кислорода, что делает её химическую формулу H2O.',
        'Подумайте о химической формуле воды, которую вы используете ежедневно.'
    FROM inserted_question
),
inserted_answer_1 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, TRUE FROM inserted_question
    RETURNING id
),
inserted_answer_1_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'H2O' FROM inserted_answer_1
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'H2O' FROM inserted_answer_1
),
inserted_answer_2 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_2_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'CO2' FROM inserted_answer_2
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'CO2' FROM inserted_answer_2
),
inserted_answer_3 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_3_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'O2' FROM inserted_answer_3
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'O2' FROM inserted_answer_3
),
inserted_answer_4 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_4_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'N2' FROM inserted_answer_4
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'N2' FROM inserted_answer_4
)
SELECT 1;

-- Вопрос 2: География
WITH
geography_topic AS (
    SELECT id FROM topic WHERE id IN (SELECT topic_id FROM topic_translation WHERE name = 'Geography' OR name = 'География' LIMIT 1)
),
inserted_question AS (
    INSERT INTO question (id, topic_id)
    VALUES (uuid_generate_v4(), (SELECT id FROM geography_topic))
    RETURNING id
),
inserted_question_translation AS (
    INSERT INTO question_translation (id, question_id, language, text, description, hint)
    SELECT gen_random_uuid(), id, 'en',
        'What is the largest desert in the world?',
        'Although deserts are often associated with heat and sand, they are defined by their extremely low precipitation levels. The Antarctic Desert, covered in ice, receives very little moisture and spans approximately 14 million square kilometers, making it the largest desert on Earth.',
        'Remember that deserts are defined by precipitation, not temperature.'
    FROM inserted_question
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru',
        'Какая самая большая пустыня в мире?',
        'Хотя пустыни часто ассоциируются с жарой и песком, они определяются чрезвычайно низким уровнем осадков. Антарктическая пустыня, покрытая льдом, получает очень мало влаги и охватывает около 14 миллионов квадратных километров, что делает её самой большой пустыней на Земле.',
        'Помните, что пустыни определяются количеством осадков, а не температурой.'
    FROM inserted_question
),
inserted_answer_1 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_1_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Sahara' FROM inserted_answer_1
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Сахара' FROM inserted_answer_1
),
inserted_answer_2 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, TRUE FROM inserted_question
    RETURNING id
),
inserted_answer_2_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Antarctic Desert' FROM inserted_answer_2
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Антарктическая пустыня' FROM inserted_answer_2
),
inserted_answer_3 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question RETURNING id
),
inserted_answer_3_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Gobi Desert' FROM inserted_answer_3
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Гоби' FROM inserted_answer_3
),
inserted_answer_4 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_4_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Kalahari Desert' FROM inserted_answer_4
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Калахари' FROM inserted_answer_4
)
SELECT 1;

-- Вопрос 3: История
WITH
history_topic AS (
    SELECT id FROM topic WHERE id IN (SELECT topic_id FROM topic_translation WHERE name = 'History' OR name = 'История' LIMIT 1)
),
inserted_question AS (
    INSERT INTO question (id, topic_id)
    VALUES (uuid_generate_v4(), (SELECT id FROM history_topic))
    RETURNING id
),
inserted_question_translation AS (
    INSERT INTO question_translation (id, question_id, language, text, description, hint)
    SELECT gen_random_uuid(), id, 'en',
        'Who was the first president of the United States?',
        'George Washington, a key figure in American history, was elected as the first president of the United States in 1789, setting many precedents for the office.',
        'He was a military leader during the American Revolutionary War.'
    FROM inserted_question
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru',
        'Кто был первым президентом США?',
        'Джордж Вашингтон, ключевая фигура в истории Америки, был избран первым президентом США в 1789 году, установив множество прецедентов для этой должности.',
        'Он был военным лидером во время Американской революционной войны.'
    FROM inserted_question
),
inserted_answer_1 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, TRUE FROM inserted_question
    RETURNING id
),
inserted_answer_1_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'George Washington' FROM inserted_answer_1
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Джордж Вашингтон' FROM inserted_answer_1
),
inserted_answer_2 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_2_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Abraham Lincoln' FROM inserted_answer_2
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Авраам Линкольн' FROM inserted_answer_2
),
inserted_answer_3 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_3_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Thomas Jefferson' FROM inserted_answer_3
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Томас Джефферсон' FROM inserted_answer_3
),
inserted_answer_4 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_4_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'John Adams' FROM inserted_answer_4
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Джон Адамс' FROM inserted_answer_4
)
SELECT 1;

-- Вопрос 4: Искусство
WITH
art_topic AS (
    SELECT id FROM topic WHERE id IN (SELECT topic_id FROM topic_translation WHERE name = 'Art' OR name = 'Искусство' LIMIT 1)
),
inserted_question AS (
    INSERT INTO question (id, topic_id)
    VALUES (uuid_generate_v4(), (SELECT id FROM art_topic))
    RETURNING id
),
inserted_question_translation AS (
    INSERT INTO question_translation (id, question_id, language, text, description, hint)
    SELECT gen_random_uuid(), id, 'en',
        'Who painted the Mona Lisa?',
        'Leonardo da Vinci painted the Mona Lisa, one of the most famous artworks in history, renowned for its enigmatic smile and masterful technique during the Italian Renaissance.',
        'This Italian artist was also a scientist, inventor, and polymath from the Renaissance period.'
    FROM inserted_question
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru',
        'Кто нарисовал Мону Лизу?',
        'Леонардо да Винчи написал "Мону Лизу" — одну из самых известных картин в истории, прославленную за загадочную улыбку и мастерскую технику эпохи итальянского Возрождения.',
        'Этот итальянский художник также был учёным, изобретателем и полиматом эпохи Возрождения.'
    FROM inserted_question
),
inserted_answer_1 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, TRUE FROM inserted_question
    RETURNING id
),
inserted_answer_1_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Leonardo da Vinci' FROM inserted_answer_1
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Леонардо да Винчи' FROM inserted_answer_1
),
inserted_answer_2 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_2_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Vincent van Gogh' FROM inserted_answer_2
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Винсент ван Гог' FROM inserted_answer_2
),
inserted_answer_3 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_3_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Pablo Picasso' FROM inserted_answer_3
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Пабло Пикассо' FROM inserted_answer_3
),
inserted_answer_4 AS (
    INSERT INTO answer (id, question_id, is_correct)
    SELECT gen_random_uuid(), id, FALSE FROM inserted_question
    RETURNING id
),
inserted_answer_4_translation AS (
    INSERT INTO answer_translation (id, answer_id, language, text)
    SELECT gen_random_uuid(), id, 'en', 'Claude Monet' FROM inserted_answer_4
    UNION ALL
    SELECT gen_random_uuid(), id, 'ru', 'Клод Моне' FROM inserted_answer_4
)
SELECT 1;