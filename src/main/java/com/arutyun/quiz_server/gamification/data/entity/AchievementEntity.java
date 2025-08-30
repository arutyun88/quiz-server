package com.arutyun.quiz_server.gamification.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Achievement entity in the gamification system.
 * <p>
 * Achievements represent rewards that users can earn by performing various
 * actions in the quiz system. Each achievement has a unique code and point
 * value.
 * Names and descriptions are stored in separate translation tables.
 * <p>
 * Achievements are categorized for better organization:
 * </p>
 * <ul>
 * <li>BEGINNER - for new users</li>
 * <li>PROGRESS - for number of questions answered</li>
 * <li>ACCURACY - for answer accuracy</li>
 * <li>STREAK - for consistency</li>
 * <li>POINTS - for point accumulation</li>
 * </ul>
 * <hr>
 * <p>
 * Сущность достижения в системе геймификации.
 * <p>
 * Достижения представляют собой награды, которые пользователи могут получить
 * за выполнение различных действий в системе викторин. Каждое достижение имеет
 * уникальный код и количество очков. Названия и описания хранятся в отдельных
 * таблицах переводов.
 * <p>
 * Достижения разделены по категориям для лучшей организации:
 * <ul>
 * <li>BEGINNER - для новичков</li>
 * <li>PROGRESS - за количество вопросов</li>
 * <li>ACCURACY - за точность ответов</li>
 * <li>STREAK - за постоянство</li>
 * <li>POINTS - за накопление очков</li>
 * </ul>
 */

@Entity
@Table(name = "achievements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AchievementEntity {

    /**
     * Unique identifier for the achievement.
     * <hr>
     * Уникальный идентификатор достижения.
     */
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    /**
     * Unique achievement code used for business logic identification.
     * <hr>
     * Уникальный код достижения, используемый для идентификации в бизнес-логике.
     */
    @Column(nullable = false, unique = true)
    private String code;

    /**
     * Points awarded to the user when the achievement is unlocked.
     * <hr>
     * Количество очков, начисляемых пользователю при получении достижения.
     */
    @Column(nullable = false)
    private Integer points;

    /**
     * Achievement category for grouping achievements by type.
     * <hr>
     * Категория достижения для группировки по типам (BEGINNER, PROGRESS, ACCURACY,
     * STREAK, POINTS).
     */
    @Column(nullable = false)
    private String category;

    /**
     * Date and time when the achievement was created.
     * <hr>
     * Дата и время создания достижения.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Sets the creation date when saving a new entity.
     * Called automatically before saving to the database.
     * <hr>
     * Устанавливает дату создания при сохранении новой сущности.
     * Вызывается автоматически перед сохранением в базу данных.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
