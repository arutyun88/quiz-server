package com.arutyun.quiz_server.question.converter;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.question.data.entity.QuestionEntity;
import com.arutyun.quiz_server.question.dto.ResponseLocalizedQuestionDto;
import com.arutyun.quiz_server.question.dto.ResponseQuestionDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QuestionDtoConverter implements DtoConverter<ResponseQuestionDto, QuestionEntity> {
    @Override
    public ResponseQuestionDto convert(QuestionEntity data) {
        return new ResponseQuestionDto(
                data.getId(),
                data.getCorrectAnswer(),
                data.getQuestion()
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> new ResponseLocalizedQuestionDto(
                                                entry.getValue().getQuestion(),
                                                entry.getValue().getDescription(),
                                                entry.getValue().getAnswers()
                                        )
                                )
                        )
        );
    }
}