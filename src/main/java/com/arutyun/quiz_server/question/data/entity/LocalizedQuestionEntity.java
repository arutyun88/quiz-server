package com.arutyun.quiz_server.question.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class LocalizedQuestionEntity {
    private String question;
    private String description;
    private Map<String, String> answers;
}
