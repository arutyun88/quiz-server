package com.arutyun.quiz_server.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseTokenDto(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken
) {
}
