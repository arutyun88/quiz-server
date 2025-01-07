package com.arutyun.quiz_server.question.service.model;

public record UserAnswersStatistic(
        int rightCount,
        int wrongCount,
        boolean lastIsRight
) {
}
