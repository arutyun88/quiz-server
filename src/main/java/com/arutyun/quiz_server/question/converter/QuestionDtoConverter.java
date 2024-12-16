package com.arutyun.quiz_server.question.converter;

import com.arutyun.quiz_server.common.dto.converter.DtoConverter;
import com.arutyun.quiz_server.question.data.entity.QuestionEntity;
import com.arutyun.quiz_server.question.dto.ResponseAnswerDto;
import com.arutyun.quiz_server.question.dto.ResponseQuestionDto;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class QuestionDtoConverter implements DtoConverter<ResponseQuestionDto, QuestionEntity> {
    @Override
    public ResponseQuestionDto convert(QuestionEntity data) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        String locale = attributes.getRequest().getHeader("X-Lang");

        var translation = data.getTranslations().stream()
                .filter(t -> t.getLanguage().equals(locale))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Translation not found"));

        return new ResponseQuestionDto(
                data.getId(),
                translation.getText(),
                translation.getDescription(),
                data.getAnswers().stream()
                        .map(
                                answer -> new ResponseAnswerDto(
                                        answer.getId(),
                                        answer.isCorrect(),
                                        answer.getTranslations().stream()
                                                .filter(t -> t.getLanguage().equals(locale))
                                                .findFirst()
                                                .orElseThrow(() -> new RuntimeException("Translation not found"))
                                                .getText()
                                )
                        ).toList()
        );
    }
}