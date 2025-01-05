package com.arutyun.quiz_server.question.converter;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;
import com.arutyun.quiz_server.question.dto.ResponseUserAnswerDto;
import org.springframework.stereotype.Component;

@Component
public class UserAnswerDtoConverter implements DtoConverter<ResponseUserAnswerDto, UserQuestionLog> {
    @Override
    public ResponseUserAnswerDto convert(UserQuestionLog data) {
        return new ResponseUserAnswerDto(
                data.getQuestion().getId(),
                data.getAnswer().getId(),
                data.getIsCorrect()
        );
    }
}
