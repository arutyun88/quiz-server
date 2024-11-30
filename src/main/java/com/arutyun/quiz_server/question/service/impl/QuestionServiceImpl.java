package com.arutyun.quiz_server.question.service.impl;

import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.question.data.entity.QuestionEntity;
import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;
import com.arutyun.quiz_server.question.data.repository.QuestionRepository;
import com.arutyun.quiz_server.question.data.repository.UserQuestionLogRepository;
import com.arutyun.quiz_server.question.service.QuestionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final UserQuestionLogRepository questionLogRepository;

    @Override
    @Transactional
    public List<QuestionEntity> getRandomQuestions(UserEntity user, int limit) {
        if (user == null) {
            return questionRepository.findRandomQuestions(limit);
        } else {
            List<QuestionEntity> questions = questionRepository.findRandomQuestionsExcludingUserAnswered(
                    user.getId(),
                    limit
            );

            logQuestionsForUser(user, questions);
            return questions;
        }
    }

    private void logQuestionsForUser(UserEntity user, List<QuestionEntity> questions) {
        List<UserQuestionLog> logs = questions.stream()
                .map(question -> new UserQuestionLog(user, question))
                .toList();

        questionLogRepository.saveAll(logs);
    }
}
