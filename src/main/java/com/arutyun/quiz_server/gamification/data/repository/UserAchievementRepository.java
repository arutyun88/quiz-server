package com.arutyun.quiz_server.gamification.data.repository;

import com.arutyun.quiz_server.gamification.data.entity.UserAchievementEntity;
import com.arutyun.quiz_server.gamification.data.projection.UserAchievementProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for managing user achievements.
 * <p>
 * Provides simple method for retrieving all user achievements with unlock status.
 * Uses optimized JOIN query to get all data in single database call.
 */
@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievementEntity, UUID> {


    /**
     * Gets unlocked achievements for user with localization.
     * <p>
     * Returns only achievements that the user has unlocked.
     * Includes localization and unlock date information.
     * <p>
     * Sorting: by unlock date DESC (newest unlocked achievements first)
     */
    @Query(
            value = """
                    SELECT 
                        a.id as achievementId,
                        a.code as achievementCode,
                        at.name as achievementName,
                        at.description as achievementDescription,
                        a.points as achievementPoints,
                        a.category as achievementCategory,
                        ua.unlocked_at as unlockedAt
                    FROM user_achievements ua
                    INNER JOIN achievements a ON a.id = ua.achievement_id
                    INNER JOIN achievement_translations at ON a.id = at.achievement_id AND at.language_code = :languageCode
                    WHERE ua.user_id = :userId
                    ORDER BY ua.unlocked_at DESC
                    """,
            nativeQuery = true
    )
    List<UserAchievementProjection> findAllUserAchievements(
            @Param("userId") UUID userId,
            @Param("languageCode") String languageCode
    );
}
