package com.arutyun.quiz_server.question.service.impl;

import com.arutyun.quiz_server.auth.data.entity.UserEntity;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.question.data.entity.AnswerEntity;
import com.arutyun.quiz_server.question.data.entity.QuestionEntity;
import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;
import com.arutyun.quiz_server.question.data.repository.AnswerRepository;
import com.arutyun.quiz_server.question.data.repository.QuestionRepository;
import com.arutyun.quiz_server.question.data.repository.UserQuestionLogRepository;
import com.arutyun.quiz_server.question.exception.AnswerConflictException;
import com.arutyun.quiz_server.question.exception.AnswerSavingException;
import com.arutyun.quiz_server.question.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    final AnswerRepository answerRepository;
    final QuestionRepository questionRepository;
    final UserQuestionLogRepository questionLogRepository;

    @Override
    public UserQuestionLog saveUserAnswer(
            UserEntity user,
            UUID questionId,
            UUID answerId
    ) throws BaseException {
        final AnswerEntity answer = answerRepository.findById(answerId).orElseThrow(
                () -> new AnswerSavingException(String.format("Answer %s not found", answerId))
        );

        final QuestionEntity question = questionRepository.findById(questionId).orElseThrow(
                () -> new AnswerSavingException(String.format("Question %s not found", questionId))
        );

        if(user == null) {
            final UserQuestionLog questionLog = new UserQuestionLog(user, question);
            questionLog.setAnswer(answer);
            questionLog.setIsCorrect(answer.isCorrect());
            return questionLog;
        }

        final Optional<UserQuestionLog> questionLog = questionLogRepository.findByUserIdAndQuestionId(
                user.getId(),
                questionId
        );

        if (questionLog.isPresent()) {
            if(questionLog.get().getAnswer() != null) {
                throw new AnswerConflictException(
                        String.format(
                                "Question %s from question %s already answered",
                                answerId,
                                questionId
                        )
                );
            }
            questionLog.get().setAnswer(answer);
            questionLog.get().setIsCorrect(answer.isCorrect());
            return questionLogRepository.save(questionLog.get());
        }

        throw new AnswerSavingException(String.format("Answer %s from question %s not saved", answerId, questionId));
    }
}
