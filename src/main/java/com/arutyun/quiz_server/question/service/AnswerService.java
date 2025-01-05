package com.arutyun.quiz_server.question.service;

import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;

import java.util.UUID;

public interface AnswerService {
    UserQuestionLog saveUserAnswer(UserEntity user, UUID questionId, UUID answerId) throws BaseException;
}
