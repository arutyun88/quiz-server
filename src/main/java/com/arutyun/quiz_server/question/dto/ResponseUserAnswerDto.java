package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ResponseUserAnswerDto(
        @JsonProperty("question_id") UUID question,
        @JsonProperty("answer_id") UUID answer,
        @JsonProperty("is_correct") boolean isCorrect
) {
}
