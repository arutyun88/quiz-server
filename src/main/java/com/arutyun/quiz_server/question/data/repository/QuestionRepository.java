package com.arutyun.quiz_server.question.data.repository;

import com.arutyun.quiz_server.question.data.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {

    @Query(
            value = """
                    SELECT q.* FROM question q
                    JOIN question_translation qt ON q.id = qt.question_id
                    WHERE qt.language = :language
                    ORDER BY RANDOM()
                    LIMIT :limit
                    """,
            nativeQuery = true
    )
    List<QuestionEntity> findRandomQuestions(
            @Param("limit") int limit,
            @Param("language") String language
    );

    @Query(
            value = """
                    SELECT q.* FROM question q
                    JOIN question_translation qt ON q.id = qt.question_id
                    WHERE qt.language = :language
                    AND q.id NOT IN (
                        SELECT l.question_id FROM user_question_log l
                        WHERE l.user_id = :user_id
                    )
                    ORDER BY RANDOM()
                    LIMIT :limit
                    """,
            nativeQuery = true
    )
    List<QuestionEntity> findRandomQuestionsExcludingUserAnswered(
            @Param("user_id") UUID userId,
            @Param("limit") int limit,
            @Param("language") String language
    );

    @Query(
            value = """
                    SELECT COUNT(*) FROM question q
                    JOIN question_translation qt ON q.id = qt.question_id
                    WHERE qt.language = :language
                    """,
            nativeQuery = true
    )
    int countTotalQuestions(
            @Param("language") String language
    );

    @Query(
            value = """
                    SELECT COUNT(*) FROM question q
                    JOIN question_translation qt ON q.id = qt.question_id
                    WHERE qt.language = :language
                    AND q.id NOT IN (
                        SELECT l.question_id FROM user_question_log l
                        WHERE l.user_id = :user_id
                    )
                    """,
            nativeQuery = true
    )
    int countTotalQuestionsExcludingUserAnswered(
            @Param("user_id") UUID userId,
            @Param("language") String language
    );

}