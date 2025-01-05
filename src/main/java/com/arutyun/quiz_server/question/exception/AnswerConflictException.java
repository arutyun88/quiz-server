package com.arutyun.quiz_server.question.exception;

import com.arutyun.quiz_server.common.exception.BaseConflictException;

public class AnswerConflictException extends BaseConflictException {
    public AnswerConflictException(String message) {
        super("QUESTION_ALREADY_ANSWERED", message);
    }
}