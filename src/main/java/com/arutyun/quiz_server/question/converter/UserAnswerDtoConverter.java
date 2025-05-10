package com.arutyun.quiz_server.question.converter;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.question.dto.ResponseUserAnswerDto;
import com.arutyun.quiz_server.question.service.model.UserAnswersStatistic;
import com.arutyun.quiz_server.user.converter.UserStatisticsDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAnswerDtoConverter implements DtoConverter<ResponseUserAnswerDto, UserAnswersStatistic> {
    final UserStatisticsDtoConverter statisticsDtoConverter;

    @Override
    public ResponseUserAnswerDto convert(UserAnswersStatistic data) {
        return new ResponseUserAnswerDto(
                data.lastIsRight(),
                statisticsDtoConverter.convert(data.statistics())
        );
    }
}
