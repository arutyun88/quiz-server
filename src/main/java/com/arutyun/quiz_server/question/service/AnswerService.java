package com.arutyun.quiz_server.question.service;

import com.arutyun.quiz_server.question.service.model.UserQuestionAnswer;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.common.exception.BaseException;

import java.util.List;
import java.util.UUID;

public interface AnswerService {
    boolean saveUserAnswer(
            UserEntity user,
            UUID questionId,
            UUID answerId
    ) throws BaseException;

    boolean checkAnswerState(
            UserEntity user,
            UUID questionId
    ) throws BaseException;

    void saveUserAnswers(UserEntity user, List<UserQuestionAnswer> answers);
}
