package com.arutyun.quiz_server.question.service.impl;

import com.arutyun.quiz_server.question.data.entity.QuestionEntity;
import com.arutyun.quiz_server.question.data.repository.QuestionRepository;
import com.arutyun.quiz_server.question.exception.QuestionNotFoundException;
import com.arutyun.quiz_server.question.service.model.UserQuestionAnswer;
import com.arutyun.quiz_server.user.data.entity.UserEntity;
import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.question.data.entity.AnswerEntity;
import com.arutyun.quiz_server.question.data.entity.UserQuestionLog;
import com.arutyun.quiz_server.question.data.repository.AnswerRepository;
import com.arutyun.quiz_server.question.data.repository.UserQuestionLogRepository;
import com.arutyun.quiz_server.question.exception.AnswerConflictException;
import com.arutyun.quiz_server.question.exception.AnswerSavingException;
import com.arutyun.quiz_server.question.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    final AnswerRepository answerRepository;
    final QuestionRepository questionRepository;
    final UserQuestionLogRepository questionLogRepository;

    @Override
    public boolean saveUserAnswer(
            UserEntity user,
            UUID questionId,
            UUID answerId
    ) throws BaseException {
        final AnswerEntity answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerSavingException(String.format("Answer %s not found", answerId)));

        if (user == null) {
            return answer.isCorrect();
        }

        Optional<UserQuestionLog> questionLog = questionLogRepository.findByUserIdAndQuestionId(
                user.getId(),
                questionId
        );

        if (questionLog.isPresent() && questionLog.get().getAnswer() != null) {
            throw new AnswerConflictException(
                    String.format("Question %s already answered", questionId)
            );
        }

        if (questionLog.isEmpty()) {
            final Optional<QuestionEntity> question = questionRepository.findById(questionId);
            if (question.isEmpty()) {
                throw new QuestionNotFoundException(
                        String.format("Question %s not found", questionId)
                );
            }
            questionLogRepository.save(new UserQuestionLog(user, question.get()));
            questionLog = questionLogRepository.findByUserIdAndQuestionId(
                    user.getId(),
                    questionId
            );
        }

        if (questionLog.isPresent()) {
            questionLog.get().setAnswer(answer);
            questionLog.get().setIsCorrect(answer.isCorrect());
            questionLog.get().setAnsweredAt(Instant.now());
            questionLogRepository.save(questionLog.get());
        }

        return answer.isCorrect();
    }

    @Override
    public boolean checkAnswerState(
            UserEntity user,
            UUID questionId
    ) throws BaseException {
        final UserQuestionLog questionLog = questionLogRepository.findByUserIdAndQuestionId(
                user.getId(),
                questionId
        ).orElseThrow(
                () -> new AnswerSavingException(
                        String.format("Answer from question %s not saved", questionId)
                )
        );

        return questionLog.getAnswer() != null;
    }

    @Override
    public void saveUserAnswers(UserEntity user, List<UserQuestionAnswer> answers) {
        if (answers.isEmpty()) {
            return;
        }

        final List<UUID> questionIds = answers.stream()
                .map(UserQuestionAnswer::questionId)
                .distinct()
                .toList();

        final List<UUID> answerIds = answers.stream()
                .map(UserQuestionAnswer::answerId)
                .distinct()
                .toList();

        List<QuestionEntity> existingQuestions = questionRepository.findAllById(questionIds);
        Map<UUID, QuestionEntity> questionMap = existingQuestions.stream()
                .collect(Collectors.toMap(QuestionEntity::getId, answer -> answer));

        List<AnswerEntity> existingAnswers = answerRepository.findAllById(answerIds);
        Map<UUID, AnswerEntity> answerMap = existingAnswers.stream()
                .collect(Collectors.toMap(AnswerEntity::getId, answer -> answer));

        List<UserQuestionLog> existingLogs = questionLogRepository.findByUserIdAndQuestionIds(
                user.getId(),
                questionIds
        );

        Map<UUID, UserQuestionLog> existingLogMap = existingLogs.stream()
                .collect(
                        Collectors.toMap(
                                log -> log.getQuestion().getId(),
                                log -> log
                        )
                );

        List<UserQuestionLog> logsToSave = new ArrayList<>();

        for (UserQuestionAnswer userAnswer : answers) {
            UUID questionId = userAnswer.questionId();
            UUID answerId = userAnswer.answerId();

            QuestionEntity question = questionMap.get(questionId);
            if (question == null) {
                continue;
            }

            AnswerEntity answer = answerMap.get(answerId);
            if (answer == null) {
                continue;
            }

            UserQuestionLog log = existingLogMap.get(questionId);
            if (log == null) {
                log = new UserQuestionLog(user, question);
                log.setAnswer(answer);
                log.setIsCorrect(answer.isCorrect());
                log.setAnsweredAt(userAnswer.answeredAt());
                logsToSave.add(log);
            } else if (log.getAnswer() == null) {
                log.setAnswer(answer);
                log.setIsCorrect(answer.isCorrect());
                log.setAnsweredAt(userAnswer.answeredAt());
                logsToSave.add(log);
            }
        }

        if (!logsToSave.isEmpty()) {
            questionLogRepository.saveAll(logsToSave);
        }
    }
}
