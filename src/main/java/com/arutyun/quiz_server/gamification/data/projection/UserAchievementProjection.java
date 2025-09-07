package com.arutyun.quiz_server.gamification.data.projection;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Projection for retrieving user achievement data using optimized JOIN query.
 * <p>
 * This interface is used by Spring Data JPA for mapping results of complex
 * JOIN query without creating additional Entity. Retrieves all necessary
 * achievement data in a single query: basic information, localization,
 * unlock status and minimal information about next achievement in category.
 * <hr>
 * <p>
 * Проекция для получения данных об ачивках пользователя с помощью оптимизированного JOIN запроса.
 * <p>
 * Этот интерфейс используется Spring Data JPA для маппинга результатов
 * сложного JOIN запроса без создания дополнительной Entity. Получает все необходимые
 * данные об ачивке за один запрос: основную информацию, локализацию, статус разблокировки
 * и минимальную информацию о следующем достижении в категории.
 */
public interface UserAchievementProjection {
    /**
     * Unique identifier for the achievement.
     * <hr>
     * Уникальный идентификатор ачивки.
     */
    UUID getId();
    
    /**
     * Achievement code (slug) used for business logic identification.
     * Examples: "QUESTION_MASTER_10", "PERFECT_10", "STREAK_7"
     * <hr>
     * Код ачивки (slug) для идентификации в бизнес-логике.
     * Например: "QUESTION_MASTER_10", "PERFECT_10", "STREAK_7"
     */
    String getCode();
    
    /**
     * Points awarded to the user when the achievement is unlocked.
     * <hr>
     * Количество очков, начисляемых пользователю при получении достижения.
     */
    Integer getPoints();
    
    /**
     * Achievement category for grouping achievements by type.
     * Possible values: BEGINNER, PROGRESS, ACCURACY, STREAK, POINTS
     * <hr>
     * Категория ачивки для группировки по типам.
     * Возможные значения: BEGINNER, PROGRESS, ACCURACY, STREAK, POINTS
     */
    String getCategory();
    
    /**
     * Localized achievement name.
     * Loaded from achievement_translations table by specified language.
     * <hr>
     * Локализованное название ачивки.
     * Загружается из таблицы achievement_translations по указанному языку.
     */
    String getName();
    
    /**
     * Localized achievement description.
     * Loaded from achievement_translations table by specified language.
     * <hr>
     * Локализованное описание ачивки.
     * Загружается из таблицы achievement_translations по указанному языку.
     */
    String getDescription();
    
    /**
     * Flag indicating whether the achievement is unlocked by the user:
     * true - if record exists in user_achievements table
     * false - if achievement is not yet unlocked.
     * <hr>
     * Флаг, указывающий получено ли достижение пользователем:
     * true - если запись существует в таблице user_achievements;
     * false - если достижение еще не получено.
     */
    Boolean getIsUnlocked();
    
    /**
     * Date and time when the achievement was unlocked by the user.
     * Null - if achievement is not yet unlocked.
     * <hr>
     * Дата и время разблокировки достижения пользователем.
     * Null - если достижение еще не получено.
     */
    LocalDateTime getUnlockedAt();
    
    // Fields are populated only for current user (isCurrentUser = true)
    // and only if there is next achievement in same category with higher points
    // Поля заполняются только для текущего пользователя (isCurrentUser = true)
    // и только если есть следующее достижение в той же категории с большим количеством очков
    
    /**
     * ID of next achievement in category.
     * Null - if no next achievement exists or this is not current user
     * <hr>
     * ID следующего достижения в категории.
     * Null - если нет следующего достижения или это не текущий пользователь
     */
    UUID getNextAchievementId();
    
    /**
     * Code of next achievement in category.
     * Null - if no next achievement exists or this is not current user
     * <hr>
     * Код следующего достижения в категории.
     * Null - если нет следующего достижения или это не текущий пользователь
     */
    String getNextAchievementCode();
    
    /**
     * Name of next achievement in category.
     * Null - if no next achievement exists or this is not current user
     * <hr>
     * Название следующего достижения в категории.
     * Null - если нет следующего достижения или это не текущий пользователь
     */
    String getNextAchievementName();
}
