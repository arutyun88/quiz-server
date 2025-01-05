package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RequestUserAnswerDto(
        @JsonProperty("question_id") UUID id,
        @JsonProperty("answer_id") UUID answer
) {
}
