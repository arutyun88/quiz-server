package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseQuestionStateDto(
        @JsonProperty("question_id") String questionId,
        @JsonProperty("is_answered") boolean isAnswered
) {
}
