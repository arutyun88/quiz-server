package com.arutyun.quiz_server.question.service;

import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.question.service.model.UserAnswersStatistic;

import java.util.UUID;

public interface AnswerService {
    UserAnswersStatistic saveUserAnswer(
            UserEntity user,
            UUID questionId,
            UUID answerId
    ) throws BaseException;
}
