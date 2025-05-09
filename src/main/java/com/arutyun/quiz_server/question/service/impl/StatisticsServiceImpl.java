package com.arutyun.quiz_server.question.service.impl;

import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;
import com.arutyun.quiz_server.question.data.repository.UserQuestionLogRepository;
import com.arutyun.quiz_server.question.service.StatisticsService;
import com.arutyun.quiz_server.question.service.model.UserStatistics;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    final UserQuestionLogRepository questionLogRepository;

    @Override
    public UserStatistics fetch(UserEntity user) throws BaseException {

        final List<UserQuestionLog> allUserAnswers = questionLogRepository.findAllFromUser(user.getId());
        long correctAnswers = allUserAnswers.stream().filter(UserQuestionLog::getIsCorrect).count();
        long incorrectAnswers = allUserAnswers.size() - correctAnswers;
        return new UserStatistics((int) correctAnswers, (int) incorrectAnswers);
    }
}
