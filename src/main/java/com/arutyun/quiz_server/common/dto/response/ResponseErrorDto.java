package com.arutyun.quiz_server.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseErrorDto implements ResponseDto {
    private int code;
    private String error;
    private String message;
}
