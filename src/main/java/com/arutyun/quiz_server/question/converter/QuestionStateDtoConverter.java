package com.arutyun.quiz_server.question.converter;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.question.dto.ResponseQuestionStateDto;
import com.arutyun.quiz_server.question.service.model.QuestionState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionStateDtoConverter implements DtoConverter<ResponseQuestionStateDto, QuestionState> {

    @Override
    public ResponseQuestionStateDto convert(QuestionState data) {
        return new ResponseQuestionStateDto(
                data.questionId(),
                data.isAnswered()
        );
    }
}
