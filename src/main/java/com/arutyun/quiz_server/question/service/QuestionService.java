package com.arutyun.quiz_server.question.service;

import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.question.data.entity.QuestionEntity;

import java.util.List;

public interface QuestionService {
    List<QuestionEntity> getRandomQuestions(UserEntity user, int limit);
}
