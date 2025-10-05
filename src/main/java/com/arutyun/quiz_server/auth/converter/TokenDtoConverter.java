package com.arutyun.quiz_server.auth.converter;

import com.arutyun.quiz_server.auth.data.entity.TokenEntity;
import com.arutyun.quiz_server.auth.dto.ResponseTokenDto;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class TokenDtoConverter implements Function<TokenEntity, ResponseTokenDto> {
    @Override
    public ResponseTokenDto apply(TokenEntity data) {
        return new ResponseTokenDto(
                data.getAccessToken()
        );
    }
}
