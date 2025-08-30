package com.arutyun.quiz_server.user.converter;

import com.arutyun.quiz_server.question.service.model.UserStatistics;
import com.arutyun.quiz_server.user.dto.UserStatisticsDto;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class UserStatisticsDtoConverter implements Function<UserStatistics, UserStatisticsDto> {
    @Override
    public UserStatisticsDto apply(UserStatistics data) {
        return new UserStatisticsDto(
                data.rightCount(),
                data.wrongCount()
        );
    }
}
