package com.arutyun.quiz_server.question.service.impl;

import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.common.model.Meta;
import com.arutyun.quiz_server.common.model.DataMeta;
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
    public DataMeta<QuestionEntity> getRandomQuestions(UserEntity user, int limit, String language) {
        List<QuestionEntity> questions;
        int total = questionRepository.countTotalQuestions(language);
        int offset;
        if (user == null) {
            questions = questionRepository.findRandomQuestions(
                    limit,
                    language
            );
            offset = 0;
        } else {
            questions = questionRepository.findRandomQuestionsExcludingUserAnswered(
                    user.getId(),
                    limit,
                    language
            );
            offset = total - questionRepository.countTotalQuestionsExcludingUserAnswered(user.getId(), language);

            logQuestionsForUser(user, questions);
        }
        return new DataMeta<>(questions, new Meta(limit, offset, total));
    }

    private void logQuestionsForUser(UserEntity user, List<QuestionEntity> questions) {
        List<UserQuestionLog> logs = questions.stream()
                .map(question -> new UserQuestionLog(user, question))
                .toList();

        questionLogRepository.saveAll(logs);
    }
}
