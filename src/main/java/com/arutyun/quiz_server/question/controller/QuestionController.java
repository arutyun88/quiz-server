package com.arutyun.quiz_server.question.controller;

import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.auth.service.UserService;
import com.arutyun.quiz_server.common.dto.response.ResponseDto;
import com.arutyun.quiz_server.common.dto.response.ResponseWrapper;
import com.arutyun.quiz_server.question.converter.QuestionDtoConverter;
import com.arutyun.quiz_server.question.data.entity.QuestionEntity;
import com.arutyun.quiz_server.question.service.QuestionService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final QuestionDtoConverter questionDtoConverter;

    @GetMapping("api/questions")
    public ResponseEntity<ResponseDto> questions(
            @Min(value = 1, message = "X-Limit must be at least 1")
            @Max(value = 50, message = "X-Limit cannot exceed 50")
            @RequestHeader(value = "X-Limit", required = false, defaultValue = "1") int limit
    ) {
        final UserEntity user = userService.getCurrentUser();
        List<QuestionEntity> questions = questionService.getRandomQuestions(user, limit);
        return ResponseWrapper.ok(questions, questionDtoConverter);
    }
}
