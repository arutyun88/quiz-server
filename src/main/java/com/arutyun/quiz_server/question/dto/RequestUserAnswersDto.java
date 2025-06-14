package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record RequestUserAnswersDto(
        @JsonProperty("questions") List<RequestUserAnswerDto> questions
) {
}
