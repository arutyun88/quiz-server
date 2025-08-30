package com.arutyun.quiz_server.question.converter;

import com.arutyun.quiz_server.question.dto.ResponseUserAnswerDto;
import com.arutyun.quiz_server.question.service.model.UserAnswersStatistic;
import com.arutyun.quiz_server.user.converter.UserStatisticsDtoConverter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAnswerDtoConverter implements Function<UserAnswersStatistic, ResponseUserAnswerDto> {
    final UserStatisticsDtoConverter statisticsDtoConverter;

    @Override
    public ResponseUserAnswerDto apply(UserAnswersStatistic data) {
        return new ResponseUserAnswerDto(
                data.lastIsRight(),
                statisticsDtoConverter.apply(data.statistics()));
    }
}
