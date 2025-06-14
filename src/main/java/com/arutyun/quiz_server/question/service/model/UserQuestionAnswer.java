package com.arutyun.quiz_server.question.service.model;

import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

public record UserQuestionAnswer(
        UUID questionId,
        UUID answerId,
        @Nullable Instant answeredAt
) {
}
