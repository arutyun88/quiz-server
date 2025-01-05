package com.arutyun.quiz_server.question.exception;

import com.arutyun.quiz_server.common.exception.BaseConflictException;

public class AnswerSavingException extends BaseConflictException {
    public AnswerSavingException(String message) {
        super("ANSWER_NOT_SAVING", message);
    }
}