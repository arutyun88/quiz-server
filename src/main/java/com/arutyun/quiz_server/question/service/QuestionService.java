package com.arutyun.quiz_server.question.service;

import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.common.model.DataMeta;
import com.arutyun.quiz_server.question.data.entity.QuestionEntity;

public interface QuestionService {
    DataMeta<QuestionEntity> getRandomQuestions(UserEntity user, int limit, String language);
}
