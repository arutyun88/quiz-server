package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseUserAnswerDto(
        @JsonProperty("right_count") int right,
        @JsonProperty("wrong_count") int wrong,
        @JsonProperty("last_is_correct") boolean lastIsCorrect
) {
}
