package com.arutyun.quiz_server.question.data.entity;

import com.arutyun.quiz_server.user.data.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(
        name = "user_question_log",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "user_id",
                                "question_id"
                        }
                )
        }
)
@NoArgsConstructor
@Getter
public class UserQuestionLog {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    @Setter
    private AnswerEntity answer;

    @Column(name = "is_correct")
    @Setter
    private Boolean isCorrect;

    public UserQuestionLog(
            UserEntity user,
            QuestionEntity question
    ) {
        this.user = user;
        this.question = question;
    }
}
