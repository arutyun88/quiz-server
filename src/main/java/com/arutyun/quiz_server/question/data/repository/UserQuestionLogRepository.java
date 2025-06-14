package com.arutyun.quiz_server.question.data.repository;

import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserQuestionLogRepository extends JpaRepository<UserQuestionLog, UUID> {
    Optional<UserQuestionLog> findByUserIdAndQuestionId(
            UUID userId,
            UUID questionId
    );

    @Query(
            value = """
                    SELECT * from user_question_log l
                    WHERE l.user_id = :user_id
                    AND l.answer_id IS NOT NULL
                    """,
            nativeQuery = true
    )
    List<UserQuestionLog> findAllFromUser(
            @Param("user_id") UUID userId
    );

    @Query(
            value = """
                SELECT * from user_question_log l
                WHERE l.user_id = :user_id
                AND l.question_id IN :question_ids
                """,
            nativeQuery = true
    )
    List<UserQuestionLog> findByUserIdAndQuestionIds(
            @Param("user_id") UUID userId,
            @Param("question_ids") List<UUID> questionIds
    );
}
