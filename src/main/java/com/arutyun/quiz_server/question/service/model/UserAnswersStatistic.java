package com.arutyun.quiz_server.question.service.model;

public record UserAnswersStatistic(
        boolean lastIsRight,
        UserStatistics statistics
) {
}
