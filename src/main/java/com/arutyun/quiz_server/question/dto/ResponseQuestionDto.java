package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.UUID;

public record ResponseQuestionDto(
        @JsonProperty("id") UUID id,
        @JsonProperty("correct_answer") char correctAnswer,
        @JsonProperty("question") Map<String, ResponseLocalizedQuestionDto> question
) {
}
