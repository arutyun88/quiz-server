package com.arutyun.quiz_server.user.converter;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.user.data.entity.PublicUserEntity;
import com.arutyun.quiz_server.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements DtoConverter<UserDto, PublicUserEntity> {
    @Override
    public UserDto convert(PublicUserEntity data) {
        return new UserDto(
                data.getId(),
                data.getUsername()
        );
    }
}
