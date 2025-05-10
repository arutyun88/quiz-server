package com.arutyun.quiz_server.question.controller;

import com.arutyun.quiz_server.question.service.StatisticsService;
import com.arutyun.quiz_server.question.service.model.UserStatistics;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.user.service.UserService;
import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.common.model.DataMeta;
import com.arutyun.quiz_server.question.converter.QuestionDtoConverter;
import com.arutyun.quiz_server.question.converter.UserAnswerDtoConverter;
import com.arutyun.quiz_server.question.data.entity.QuestionEntity;
import com.arutyun.quiz_server.question.dto.RequestUserAnswerDto;
import com.arutyun.quiz_server.question.service.AnswerService;
import com.arutyun.quiz_server.question.service.QuestionService;
import com.arutyun.quiz_server.question.service.model.UserAnswersStatistic;
import com.arutyun.quiz_server.user.exception.UserNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    private final StatisticsService statisticsService;
    private final QuestionDtoConverter questionDtoConverter;
    private final UserAnswerDtoConverter userAnswerDtoConverter;

    @GetMapping("api/questions")
    public ResponseEntity<ResponseDto> questions(
            @Min(value = 1, message = "X-Limit must be at least 1")
            @Max(value = 50, message = "X-Limit cannot exceed 50")
            @RequestHeader(value = "X-Limit", required = false, defaultValue = "1") int limit,
            @RequestHeader(value = "X-Lang") String language
    ) {
        UserEntity user = null;
        try {
            user = userService.getCurrentUser();
        } catch (UserNotFoundException ignored) {
        }

        DataMeta<QuestionEntity> result = questionService.getRandomQuestions(user, limit, language);
        return ResponseWrapper.ok(
                result.getData(),
                questionDtoConverter,
                result.getMeta()
        );
    }

    @PostMapping("api/questions/answer")
    public ResponseEntity<ResponseDto> userAnswer(
            @Valid @RequestBody RequestUserAnswerDto answer
    ) throws BaseException {
        final UserEntity user = userService.getCurrentUser();
        final boolean isCorrect = answerService.saveUserAnswer(
                user,
                answer.questionId(),
                answer.answerId()
        );
        final UserStatistics statistics = statisticsService.fetch(user);
        return ResponseWrapper.ok(
                new UserAnswersStatistic(isCorrect, statistics),
                userAnswerDtoConverter
        );
    }
}
