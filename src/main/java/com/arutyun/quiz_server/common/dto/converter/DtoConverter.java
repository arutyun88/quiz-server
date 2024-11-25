package com.arutyun.quiz_server.common.dto.converter;

public interface DtoConverter<T, D> {
    T convert(D data);
}
