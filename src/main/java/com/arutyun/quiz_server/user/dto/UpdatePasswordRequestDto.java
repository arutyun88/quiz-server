package com.arutyun.quiz_server.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePasswordRequestDto(
        @JsonProperty("old_password") String oldPassword,
        @JsonProperty("new_password") String newPassword
) {
}
