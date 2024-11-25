package com.arutyun.quiz_server.common.dto.response;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.common.exception.BaseException;
import org.springframework.http.ResponseEntity;

public abstract class ResponseWrapper {
    private ResponseWrapper() {
        throw new IllegalStateException("ResponseWrapper is utility class");
    }

    public static <T, D> ResponseEntity<ResponseDto> ok(
            D data,
            DtoConverter<T, D> converter
    ) {
        return ResponseEntity.ok(
                new ResponseSuccessDto<T>(
                        converter.convert(data)
                )
        );
    }

    public static ResponseEntity<ResponseDto> error(
            BaseException exception
    ) {
        return ResponseEntity.status(exception.getCode()).body(
                new ResponseErrorDto(
                        exception.getCode(),
                        exception.getError(),
                        exception.getMessage()
                )
        );
    }
}
