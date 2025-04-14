package com.arutyun.quiz_server.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record UpdateUserRequestDto(
        @JsonProperty("name") String name,
        @JsonProperty("birth_date") @JsonFormat(pattern = "yyyy-MM-dd[ HH:mm:ss.SSS][VV]") LocalDate birthDate
) {
}
