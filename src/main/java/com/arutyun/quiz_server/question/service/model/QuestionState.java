package com.arutyun.quiz_server.question.service.model;

public record QuestionState(
        String questionId,
        boolean isAnswered
) {
}
