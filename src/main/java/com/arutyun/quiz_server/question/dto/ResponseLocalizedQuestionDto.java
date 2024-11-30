package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ResponseLocalizedQuestionDto(
        @JsonProperty("question") String question,
        @JsonProperty("description") String description,
        @JsonProperty("answers") Map<String, String> answers
) {
}
