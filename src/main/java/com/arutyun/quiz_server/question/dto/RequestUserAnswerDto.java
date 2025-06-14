package com.arutyun.quiz_server.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

public record RequestUserAnswerDto(
        @JsonProperty("question_id") UUID questionId,
        @JsonProperty("answer_id") UUID answerId,
        @Nullable @JsonProperty("answered_at") Instant answeredAt
) {
}
