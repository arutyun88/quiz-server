package com.arutyun.quiz_server.user.converter;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.question.service.model.UserStatistics;
import com.arutyun.quiz_server.user.dto.UserDto;
import com.arutyun.quiz_server.user.dto.UserStatisticsDto;
import org.springframework.stereotype.Component;

@Component
public class UserStatisticsDtoConverter implements DtoConverter<UserStatisticsDto, UserStatistics> {
    @Override
    public UserStatisticsDto convert(UserStatistics data) {
        return new UserStatisticsDto(
                data.rightCount(),
                data.wrongCount()
        );
    }
}
