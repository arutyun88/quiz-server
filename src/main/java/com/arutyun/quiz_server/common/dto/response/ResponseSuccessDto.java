package com.arutyun.quiz_server.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseSuccessDto<T> implements ResponseDto {
    private T data;
}
