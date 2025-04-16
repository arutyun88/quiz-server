package com.arutyun.quiz_server.common.dto.response;

import com.arutyun.quiz_server.common.model.Meta;
import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.common.exception.BaseException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class ResponseWrapper {
    private ResponseWrapper() {
        throw new IllegalStateException("ResponseWrapper is utility class");
    }

    public static <T, D> ResponseEntity<ResponseDto> ok(
            D data,
            DtoConverter<T, D> converter
    ) {
        return ResponseEntity.ok(
                ResponseSuccessDto.of(
                        converter.convert(data)
                )
        );
    }

    public static <T, D> ResponseEntity<ResponseDto> ok(
            List<D> data,
            DtoConverter<T, D> converter,
            Meta meta
    ) {
        return ResponseEntity.ok(
                ResponseSuccessDto.of(
                        data.stream()
                                .map(converter::convert)
                                .toList(),
                        metaConvert(meta)
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

    private static ResponseMetaDto metaConvert(Meta meta) {
        return new ResponseMetaDto(
                meta.limit(),
                meta.offset(),
                meta.total()
        );
    }
}
