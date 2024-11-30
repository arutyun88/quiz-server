-- Добавление кастомных вопросов в таблицу questions
INSERT INTO questions (id, correct_answer, question)
VALUES
    (
        uuid_generate_v4(),
        'a',
        '{
            "ru": {
                "question": "Какой язык программирования используется для Android-разработки?",
                "description": "Укажите основной язык для создания Android-приложений.",
                "answers": {
                    "a": "Kotlin",
                    "b": "Java",
                    "c": "Python",
                    "d": "Swift"
                }
            },
            "en": {
                "question": "What programming language is used for Android development?",
                "description": "Specify the main language for creating Android applications.",
                "answers": {
                    "a": "Kotlin",
                    "b": "Java",
                    "c": "Python",
                    "d": "Swift"
                }
            }
        }'
    ),
    (
        uuid_generate_v4(),
        'b',
        '{
            "ru": {
                "question": "Кто является основателем компании Microsoft?",
                "description": "Выберите правильный ответ из предложенных.",
                "answers": {
                    "a": "Стив Джобс",
                    "b": "Билл Гейтс",
                    "c": "Марк Цукерберг",
                    "d": "Илон Маск"
                }
            },
            "en": {
                "question": "Who is the founder of Microsoft?",
                "description": "Choose the correct answer from the options provided.",
                "answers": {
                    "a": "Steve Jobs",
                    "b": "Bill Gates",
                    "c": "Mark Zuckerberg",
                    "d": "Elon Musk"
                }
            }
        }'
    ),
    (
        uuid_generate_v4(),
        'd',
        '{
            "ru": {
                "question": "Как называется самая высокая гора в мире?",
                "description": "Выберите правильный ответ на предложенный вопрос.",
                "answers": {
                    "a": "Килиманджаро",
                    "b": "Монблан",
                    "c": "Мак-Кинли",
                    "d": "Эверест"
                }
            },
            "en": {
                "question": "What is the name of the tallest mountain in the world?",
                "description": "Choose the correct answer to the question.",
                "answers": {
                    "a": "Kilimanjaro",
                    "b": "Mont Blanc",
                    "c": "McKinley",
                    "d": "Everest"
                }
            }
        }'
    );

-- Добавление дополнительных вопросов в таблицу questions
DO $$
BEGIN
    FOR i IN 1..30 LOOP
        INSERT INTO questions (id, correct_answer, question)
        VALUES (
            uuid_generate_v4(),
            'a',
            jsonb_build_object(
                'ru', jsonb_build_object(
                    'question', 'Вопрос #' || i || ' на русском языке',
                    'description', 'Описание вопроса #' || i || ' на русском языке',
                    'answers', jsonb_build_object(
                        'a', 'Ответ A',
                        'b', 'Ответ B',
                        'c', 'Ответ C',
                        'd', 'Ответ D'
                    )
                ),
                'en', jsonb_build_object(
                    'question', 'Question #' || i || ' in English',
                    'description', 'Description for question #' || i || ' in English',
                    'answers', jsonb_build_object(
                        'a', 'Answer A',
                        'b', 'Answer B',
                        'c', 'Answer C',
                        'd', 'Answer D'
                    )
                )
            )
        );
    END LOOP;
END $$;
