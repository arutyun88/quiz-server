package com.arutyun.quiz_server.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserDto(
        @JsonProperty("id") UUID id,
        @JsonProperty("email") String email
) {
}
