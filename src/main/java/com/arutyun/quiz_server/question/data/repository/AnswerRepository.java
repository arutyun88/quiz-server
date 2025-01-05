package com.arutyun.quiz_server.question.data.repository;

import com.arutyun.quiz_server.question.data.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface AnswerRepository extends JpaRepository<AnswerEntity, UUID> {
}
