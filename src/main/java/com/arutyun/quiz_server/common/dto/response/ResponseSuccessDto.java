package com.arutyun.quiz_server.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSuccessDto<T> implements ResponseDto {
    private T data;
    private ResponseMetaDto meta;

    public static <T> ResponseSuccessDto<T> of() {
        return new ResponseSuccessDto<>(null, null);
    }

    public static <T> ResponseSuccessDto<T> of(T data) {
        return new ResponseSuccessDto<>(data, null);
    }

    public static <T> ResponseSuccessDto<T> of(T data, ResponseMetaDto meta) {
        return new ResponseSuccessDto<>(data, meta);
    }
}
