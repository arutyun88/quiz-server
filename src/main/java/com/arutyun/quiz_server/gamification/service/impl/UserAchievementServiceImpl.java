package com.arutyun.quiz_server.gamification.service.impl;

import com.arutyun.quiz_server.gamification.data.repository.UserAchievementRepository;
import com.arutyun.quiz_server.gamification.service.UserAchievementService;
import com.arutyun.quiz_server.gamification.service.model.UserAchievementModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAchievementServiceImpl implements UserAchievementService {

    private final UserAchievementRepository userAchievementRepository;

    @Override
    public List<UserAchievementModel> getUserAllAchievements(UUID userId, String languageCode) {
        return userAchievementRepository.findAllUserAchievements(userId, languageCode).stream()
                .map(userAchievement -> new UserAchievementModel(
                        userAchievement.getAchievementId(),
                        userAchievement.getAchievementName(),
                        userAchievement.getAchievementDescription(),
                        userAchievement.getAchievementPoints(),
                        userAchievement.getAchievementCategory()))
                .collect(Collectors.toList());
    }
}
