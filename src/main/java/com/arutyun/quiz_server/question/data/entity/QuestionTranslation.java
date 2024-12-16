package com.arutyun.quiz_server.question.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "question_translation")
@NoArgsConstructor
@Getter
public class QuestionTranslation {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @Column(name = "language", nullable = false, length = 2)
    private String language;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "description", nullable = false)
    private String description;
}