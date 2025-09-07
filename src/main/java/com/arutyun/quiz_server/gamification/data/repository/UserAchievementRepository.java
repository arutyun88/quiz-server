package com.arutyun.quiz_server.gamification.data.repository;

import com.arutyun.quiz_server.gamification.data.entity.UserAchievementEntity;
import com.arutyun.quiz_server.gamification.data.projection.UserAchievementProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for managing user achievements.
 * <p>
 * Provides methods for retrieving user achievements with different levels of detail:
 * full information for current user and minimal information for other users.
 * <hr>
 * <p>
 * Репозиторий для управления достижениями пользователей.
 * <p>
 * Предоставляет методы для получения достижений пользователей с разным уровнем детализации:
 * полная информация для текущего пользователя и минимальная информация для других пользователей.
 */

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievementEntity, UUID> {

    /**
     * Gets all achievements for current user with full details.
     * <p>
     * Retrieves complete achievement information including localization,
     * unlock status and next achievement in category. Uses optimized JOIN query
     * to get all data in single database call.
     * <p>
     * Sorting order:
     * 1. Unlocked achievements first (sorted by unlock date DESC)
     * 2. Locked achievements second (sorted by category and points)
     * <hr>
     * <p>
     * Получает все ачивки для текущего пользователя с полной детализацией.
     * <p>
     * Извлекает полную информацию об ачивках включая локализацию,
     * статус разблокировки и следующее достижение в категории. Использует
     * оптимизированный JOIN запрос для получения всех данных за один вызов БД.
     * <p>
     * Порядок сортировки:
     * 1. Сначала разблокированные ачивки (отсортированы по дате разблокировки DESC)
     * 2. Затем заблокированные ачивки (отсортированы по категории и очкам)
     */
    @Query(value = """
        WITH unlocked_categories AS (
            SELECT DISTINCT a.category
            FROM achievements a
            INNER JOIN user_achievements ua ON a.id = ua.achievement_id AND ua.user_id = :userId
        ),
        unlocked_achievements AS (
            SELECT 
                a.id as id,
                a.code as code,
                a.points as points,
                a.category as category,
                at.name as name,
                at.description as description,
                true as isUnlocked,
                NULL as nextAchievementId,
                NULL as nextAchievementCode,
                NULL as nextAchievementName,
                ua.unlocked_at as unlockedAt
            FROM achievements a
            INNER JOIN achievement_translations at ON a.id = at.achievement_id AND at.language_code = :languageCode
            INNER JOIN user_achievements ua ON a.id = ua.achievement_id AND ua.user_id = :userId
        ),
        locked_achievements AS (
            SELECT 
                a.id as id,
                a.code as code,
                a.points as points,
                a.category as category,
                at.name as name,
                at.description as description,
                false as isUnlocked,
                CASE WHEN next_a.id IS NOT NULL THEN next_a.id ELSE NULL END as nextAchievementId,
                CASE WHEN next_a.id IS NOT NULL THEN next_a.code ELSE NULL END as nextAchievementCode,
                CASE WHEN next_a.id IS NOT NULL THEN next_at.name ELSE NULL END as nextAchievementName,
                NULL as unlockedAt
            FROM achievements a
            INNER JOIN achievement_translations at ON a.id = at.achievement_id AND at.language_code = :languageCode
            LEFT JOIN achievements next_a ON next_a.category = a.category 
                AND next_a.points > a.points 
                AND next_a.id NOT IN (
                    SELECT ua2.achievement_id 
                    FROM user_achievements ua2 
                    WHERE ua2.user_id = :userId
                )
            LEFT JOIN achievement_translations next_at ON next_a.id = next_at.achievement_id 
                AND next_at.language_code = :languageCode
            WHERE a.category NOT IN (SELECT category FROM unlocked_categories)
        )
        SELECT * FROM unlocked_achievements
        UNION ALL
        SELECT * FROM locked_achievements
        ORDER BY 
            CASE WHEN isUnlocked THEN 0 ELSE 1 END,
            unlockedAt DESC NULLS LAST,
            category,
            points
        """, nativeQuery = true)
    List<UserAchievementProjection> findCurrentUserAchievements(
            @Param("userId") UUID userId,
            @Param("languageCode") String languageCode,
            Pageable pageable
    );

    /**
     * Gets unlocked achievements for other user with minimal details.
     * <p>
     * Retrieves only basic information about unlocked achievements:
     * ID, code and localized name. Used for displaying other users' achievements
     * without exposing sensitive progress information.
     * <p>
     * Sorting: by unlock date DESC (newest first)
     * <hr>
     * <p>
     * Получает разблокированные ачивки для другого пользователя с минимальной детализацией.
     * <p>
     * Извлекает только базовую информацию о разблокированных ачивках:
     * ID, код и локализованное название. Используется для отображения ачивок
     * других пользователей без раскрытия чувствительной информации о прогрессе.
     * <p>
     * Сортировка: по дате разблокировки DESC (новые сначала)
     */
    @Query(value = """
        SELECT 
            a.id as id,
            a.code as code,
            at.name as name
        FROM achievements a
        INNER JOIN achievement_translations at ON a.id = at.achievement_id AND at.language_code = :languageCode
        INNER JOIN user_achievements ua ON a.id = ua.achievement_id AND ua.user_id = :userId
        ORDER BY ua.unlocked_at DESC
        """, nativeQuery = true)
    List<UserAchievementProjection> findOtherUserUnlockedAchievements(
            @Param("userId") UUID userId,
            @Param("languageCode") String languageCode,
            Pageable pageable
    );

    /**
     * Counts total achievements for current user.
     * <p>
     * Counts all achievements (both unlocked and locked) for the current user.
     * Used for pagination to determine total number of pages.
     * <hr>
     * <p>
     * Подсчитывает общее количество ачивок для текущего пользователя.
     * <p>
     * Считает все ачивки (как разблокированные, так и заблокированные) для текущего пользователя.
     * Используется для пагинации для определения общего количества страниц.
     */
    @Query(value = """
        WITH unlocked_categories AS (
            SELECT DISTINCT a.category
            FROM achievements a
            INNER JOIN user_achievements ua ON a.id = ua.achievement_id AND ua.user_id = :userId
        ),
        unlocked_achievements AS (
            SELECT COUNT(*) as count FROM achievements a
            INNER JOIN user_achievements ua ON a.id = ua.achievement_id AND ua.user_id = :userId
        ),
        locked_achievements AS (
            SELECT COUNT(*) as count FROM achievements a
            WHERE a.category NOT IN (SELECT category FROM unlocked_categories)
        )
        SELECT (SELECT count FROM unlocked_achievements) + (SELECT count FROM locked_achievements)
        """, nativeQuery = true)
    Long countCurrentUserAchievements(@Param("userId") UUID userId);

    /**
     * Counts unlocked achievements for other user.
     * <p>
     * Counts only unlocked achievements for other users.
     * Used for pagination to determine total number of pages.
     * <hr>
     * <p>
     * Подсчитывает разблокированные ачивки для другого пользователя.
     * <p>
     * Считает только разблокированные ачивки для других пользователей.
     * Используется для пагинации для определения общего количества страниц.
     */
    @Query(value = """
        SELECT COUNT(DISTINCT a.id)
        FROM achievements a
        INNER JOIN user_achievements ua ON a.id = ua.achievement_id AND ua.user_id = :userId
        """, nativeQuery = true)
    Long countOtherUserUnlockedAchievements(@Param("userId") UUID userId);
}
