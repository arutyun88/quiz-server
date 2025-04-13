package com.arutyun.quiz_server.user.converter;

import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements DtoConverter<UserDto, UserEntity> {
    @Override
    public UserDto convert(UserEntity data) {
        return new UserDto(
                data.getId(),
                data.getEmail(),
                data.getName(),
                data.getBirthDate()
        );
    }
}
