package com.arutyun.quiz_server.question.service.impl;

import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.question.data.entity.AnswerEntity;
import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;
import com.arutyun.quiz_server.question.data.repository.AnswerRepository;
import com.arutyun.quiz_server.question.data.repository.UserQuestionLogRepository;
import com.arutyun.quiz_server.question.exception.AnswerConflictException;
import com.arutyun.quiz_server.question.exception.AnswerSavingException;
import com.arutyun.quiz_server.question.service.AnswerService;
import com.arutyun.quiz_server.question.service.model.UserAnswersStatistic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    final AnswerRepository answerRepository;
    final UserQuestionLogRepository questionLogRepository;

    @Override
    public UserAnswersStatistic saveUserAnswer(
            UserEntity user,
            UUID questionId,
            UUID answerId
    ) throws BaseException {
        final AnswerEntity answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerSavingException(String.format("Answer %s not found", answerId)));

        if (user == null) {
            return new UserAnswersStatistic(0, 0, answer.isCorrect());
        }

        final UserQuestionLog questionLog = questionLogRepository.findByUserIdAndQuestionId(
                user.getId(),
                questionId
        ).orElseThrow(
                () -> new AnswerSavingException(
                        String.format("Answer %s from question %s not saved", answerId, questionId)
                )
        );

        if (questionLog.getAnswer() != null) {
            throw new AnswerConflictException(
                    String.format("Question %s from question %s already answered", answerId, questionId)
            );
        }

        questionLog.setAnswer(answer);
        questionLog.setIsCorrect(answer.isCorrect());
        questionLogRepository.save(questionLog);

        final List<UserQuestionLog> allUserAnswers = questionLogRepository.findAllFromUser(user.getId());
        long correctAnswers = allUserAnswers.stream().filter(UserQuestionLog::getIsCorrect).count();
        long incorrectAnswers = allUserAnswers.size() - correctAnswers;

        return new UserAnswersStatistic((int) correctAnswers, (int) incorrectAnswers, answer.isCorrect());
    }
}
