package com.arutyun.quiz_server.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record UserDto(
        @JsonProperty("id") UUID id,
        @JsonProperty("email") String email,
        @JsonProperty("name") String name,
        @JsonProperty("birth_date") LocalDate birthDate
) {
}
