package com.group10.contestPlatform.entities;

import java.sql.Timestamp;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz_answer")
public class Answer {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "quizId", nullable = false)
    private Quiz quiz;
    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;
    @Builder.Default
    private Boolean active = true;
    @Column(nullable = false)
    private Boolean correct;
    @Builder.Default
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Builder.Default
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
    @Column(nullable = false)
    private String content;
    @OneToMany(mappedBy = "answer")
    private Set<TakeAnswer> takeAnswers;
}
