package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record ResponseQuestionDto(
        @JsonProperty("id") UUID id,
        @JsonProperty("question") String question,
        @JsonProperty("description") String description,
        @JsonProperty("answers") List<ResponseAnswerDto> answers
) {
}
