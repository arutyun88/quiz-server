package com.arutyun.quiz_server.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record UpdateUserRequestDto(
        @JsonProperty("name") String name,
        @JsonProperty("birth_date") LocalDate birthDate
) {
}
