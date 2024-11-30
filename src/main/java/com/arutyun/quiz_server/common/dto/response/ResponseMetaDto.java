package com.arutyun.quiz_server.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseMetaDto(
        @JsonProperty("limit") int limit,
        @JsonProperty("offset") int offset,
        @JsonProperty("total") int total
) {
}
