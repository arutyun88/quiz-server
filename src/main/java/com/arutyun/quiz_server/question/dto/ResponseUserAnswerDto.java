package com.arutyun.quiz_server.question.dto;

import com.arutyun.quiz_server.user.dto.UserStatisticsDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseUserAnswerDto(
        @JsonProperty("last_is_correct") boolean lastIsCorrect,
        UserStatisticsDto statistics
) {
}
