package com.arutyun.quiz_server.gamification.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Achievement translation entity for multilingual support.
 * <p>
 * Stores localized versions of achievement names and descriptions
 * for different languages. Each achievement can have multiple translations,
 * one for each supported language.
 * <p>
 * The translation is linked to the main achievement via foreign key
 * and includes language code, translated name and description.
 * <hr>
 * <p>
 * Сущность перевода достижений для многоязычной поддержки.
 * <p>
 * Хранит локализованные версии названий и описаний достижений
 * для разных языков. Каждое достижение может иметь несколько переводов,
 * по одному для каждого поддерживаемого языка.
 * <p>
 * Перевод связан с основным достижением через внешний ключ
 * и включает код языка, переведенное название и описание.
 */
@Entity
@Table(name = "achievement_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AchievementTranslationEntity {

    /**
     * Unique identifier for the translation.
     * <hr>
     * Уникальный идентификатор перевода.
     */
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    /**
     * Reference to the main achievement entity.
     * <hr>
     * Ссылка на основную сущность достижения.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id", nullable = false)
    private AchievementEntity achievement;

    /**
     * Language code for this translation (e.g., 'en', 'ru', 'es').
     * <hr>
     * Код языка для этого перевода (например, 'en', 'ru', 'es').
     */
    @Column(name = "language_code", nullable = false)
    private String languageCode;

    /**
     * Translated achievement name.
     * <hr>
     * Переведенное название достижения.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Translated achievement description.
     * <hr>
     * Переведенное описание достижения.
     */
    @Column
    private String description;

    /**
     * Date and time when the translation was created.
     * <hr>
     * Дата и время создания перевода.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Date and time when the translation was last updated.
     * <hr>
     * Дата и время последнего обновления перевода.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Sets the creation and update dates when saving a new entity.
     * Called automatically before saving to the database.
     * <hr>
     * Устанавливает даты создания и обновления при сохранении новой сущности.
     * Вызывается автоматически перед сохранением в базу данных.
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    /**
     * Updates the modification date when updating an entity.
     * Called automatically before updating in the database.
     * <hr>
     * Обновляет дату изменения при обновлении сущности.
     * Вызывается автоматически перед обновлением в базе данных.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
