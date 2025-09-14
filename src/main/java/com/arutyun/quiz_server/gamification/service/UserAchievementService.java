package com.arutyun.quiz_server.gamification.service;

import java.util.List;
import java.util.UUID;

import com.arutyun.quiz_server.gamification.service.model.UserAchievementModel;

/**
 * Service for working with user achievements.
 * <p>
 * Provides methods for obtaining user unlocked achievements with localization.
 */
public interface UserAchievementService {

    /**
     * Get unlocked achievements for user with localization.
     * <p>
     * Returns only achievements that the user has unlocked.
     * Includes localization and unlock date information.
     * <p>
     * Sorting: by unlock date DESC (newest unlocked achievements first)
     */
    List<UserAchievementModel> getUserAllAchievements(UUID userId, String languageCode);
}
