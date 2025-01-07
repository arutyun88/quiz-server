package com.arutyun.quiz_server.question.converter;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.question.dto.ResponseUserAnswerDto;
import com.arutyun.quiz_server.question.service.model.UserAnswersStatistic;
import org.springframework.stereotype.Component;

@Component
public class UserAnswerDtoConverter implements DtoConverter<ResponseUserAnswerDto, UserAnswersStatistic> {
    @Override
    public ResponseUserAnswerDto convert(UserAnswersStatistic data) {
        return new ResponseUserAnswerDto(
                data.rightCount(),
                data.wrongCount(),
                data.lastIsRight()
        );
    }
}
