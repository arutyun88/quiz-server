package com.arutyun.quiz_server.gamification.data.entity;

import com.arutyun.quiz_server.user.data.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * User achievement entity representing the relationship between users and
 * achievements.
 *
 * <p>
 * This entity represents the connection between a user and an achievement,
 * which is created when a user earns an achievement. Each record contains
 * information about when the user unlocked the achievement.
 * </p>
 *
 * <p>
 * One user can earn multiple achievements, and one achievement can be
 * earned by multiple users (many-to-many relationship).
 * </p>
 *
 * <p>
 * The entity tracks the exact moment when an achievement was unlocked,
 * allowing for progress tracking and historical analysis.
 * </p>
 *
 * <hr>
 *
 * <p>
 * Сущность связи пользователя с достижением.
 * </p>
 *
 * <p>
 * Эта сущность представляет собой связь между пользователем и достижением,
 * которая создается когда пользователь получает достижение. Каждая запись
 * содержит информацию о том, когда пользователь разблокировал достижение.
 * </p>
 *
 * <p>
 * Один пользователь может получить множество достижений, и одно достижение
 * может быть получено множеством пользователей (многие-ко-многим).
 * </p>
 *
 * <p>
 * Сущность отслеживает точный момент разблокировки достижения,
 * что позволяет анализировать прогресс и историю.
 * </p>
 */

@Entity
@Table(name = "user_achievements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAchievementEntity {

    /**
     * Unique identifier for the user achievement record.
     *
     * <hr>
     *
     * Уникальный идентификатор записи пользовательского достижения.
     */
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    /**
     * User who earned the achievement.
     *
     * <hr>
     *
     * Пользователь, получивший достижение.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * Achievement that was earned by the user.
     *
     * <hr>
     *
     * Достижение, которое получил пользователь.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id", nullable = false)
    private AchievementEntity achievement;

    /**
     * Date and time when the user unlocked the achievement.
     *
     * <hr>
     *
     * Дата и время получения достижения пользователем.
     */
    @Column(name = "unlocked_at", nullable = false)
    private LocalDateTime unlockedAt;

    /**
     * Sets the unlock date when saving a new user achievement.
     * Called automatically before saving to the database.
     *
     * <hr>
     *
     * Устанавливает дату разблокировки при сохранении нового пользовательского
     * достижения.
     * Вызывается автоматически перед сохранением в базу данных.
     */
    @PrePersist
    protected void onCreate() {
        unlockedAt = LocalDateTime.now();
    }
}
