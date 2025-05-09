package com.arutyun.quiz_server.question.service;

import com.arutyun.quiz_server.common.exception.BaseException;
import com.arutyun.quiz_server.question.service.model.UserStatistics;
import com.arutyun.quiz_server.user.data.entity.UserEntity;

public interface StatisticsService {
    UserStatistics fetch(
            UserEntity user
    ) throws BaseException;
}
