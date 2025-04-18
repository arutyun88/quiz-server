package com.arutyun.quiz_server.question.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "topic_translation")
@NoArgsConstructor
@Getter
public class TopicTranslation {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;

    @Column(name = "language", nullable = false, length = 2)
    private String language;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;
}
