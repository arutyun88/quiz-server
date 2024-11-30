package com.arutyun.quiz_server.question.data.repository;

import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserQuestionLogRepository extends JpaRepository<UserQuestionLog, UUID> {
}
