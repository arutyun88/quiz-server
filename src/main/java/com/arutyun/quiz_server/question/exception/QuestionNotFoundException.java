package com.arutyun.quiz_server.question.exception;

import com.arutyun.quiz_server.common.exception.BaseNotFoundException;

public class QuestionNotFoundException extends BaseNotFoundException {
    public QuestionNotFoundException(String message) {
        super("QUESTION_NOT_FOUND", message);
    }
}