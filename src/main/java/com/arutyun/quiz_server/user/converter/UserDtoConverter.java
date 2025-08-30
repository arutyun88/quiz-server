package com.arutyun.quiz_server.user.converter;

import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.user.dto.UserDto;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Function<UserEntity, UserDto> {
    @Override
    public UserDto apply(UserEntity data) {
        return new UserDto(
                data.getId(),
                data.getEmail(),
                data.getName(),
                data.getBirthDate()
                );
    }
}
