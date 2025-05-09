package com.arutyun.quiz_server.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserStatisticsDto(
        @JsonProperty("right_count") int right,
        @JsonProperty("wrong_count") int wrong
) {
}
