package com.arutyun.quiz_server.question.converter;

import com.arutyun.quiz_server.question.dto.ResponseQuestionStateDto;
import com.arutyun.quiz_server.question.service.model.QuestionState;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionStateDtoConverter implements Function<QuestionState, ResponseQuestionStateDto> {

    @Override
    public ResponseQuestionStateDto apply(QuestionState data) {
        return new ResponseQuestionStateDto(
                data.questionId(),
                data.isAnswered());
    }
}
