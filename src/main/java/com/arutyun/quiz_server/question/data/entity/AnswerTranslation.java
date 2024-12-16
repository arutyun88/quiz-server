package com.arutyun.quiz_server.question.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "answer_translation")
@NoArgsConstructor
@Getter
public class AnswerTranslation {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    @Column(name = "language", nullable = false, length = 2)
    private String language;

    @Column(name = "text", nullable = false)
    private String text;
}