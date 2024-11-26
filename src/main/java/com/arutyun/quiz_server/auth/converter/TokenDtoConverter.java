package com.arutyun.quiz_server.auth.converter;

import com.arutyun.quiz_server.auth.data.entity.TokenEntity;
import com.arutyun.quiz_server.auth.dto.ResponseTokenDto;
import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import org.springframework.stereotype.Component;

@Component
public class TokenDtoConverter implements DtoConverter<ResponseTokenDto, TokenEntity> {
    @Override
    public ResponseTokenDto convert(TokenEntity data) {
        return new ResponseTokenDto(
                data.getAccessToken(),
                data.getRefreshToken()
        );
    }
}
