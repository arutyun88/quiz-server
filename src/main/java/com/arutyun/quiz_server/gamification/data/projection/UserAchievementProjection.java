package com.arutyun.quiz_server.gamification.data.projection;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Simple projection for user achievements.
 * <p>
 * This interface provides essential information about user's achievements:
 * id, code, name, description, points, category and unlock date.
 */
public interface UserAchievementProjection {
    
    /**
     * Achievement ID.
     */
    UUID getAchievementId();
    
    /**
     * Achievement code.
     */
    String getAchievementCode();
    
    /**
     * Achievement name (localized).
     */
    String getAchievementName();
    
    /**
     * Achievement description (localized).
     */
    String getAchievementDescription();
    
    /**
     * Achievement points.
     */
    Integer getAchievementPoints();
    
    /**
     * Achievement category.
     */
    String getAchievementCategory();
    
    /**
     * Date when achievement was unlocked.
     * Null if not unlocked.
     */
    LocalDateTime getUnlockedAt();
}