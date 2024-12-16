package com.arutyun.quiz_server.question.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "answer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AnswerEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "translations", nullable = false)
    private List<AnswerTranslation> translations;
}