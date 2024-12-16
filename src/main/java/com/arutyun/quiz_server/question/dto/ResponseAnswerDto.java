package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ResponseAnswerDto(
        @JsonProperty("id") UUID id,
        @JsonProperty("is_correct") boolean isCorrect,
        @JsonProperty("answer") String answer
) {
}
