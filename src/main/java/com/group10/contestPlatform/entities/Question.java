package com.group10.contestPlatform.entities;

import java.sql.Timestamp;
import java.util.List;
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
@Table(name = "quiz_question")
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    // @Column(nullable = false)
    // private String type;
    @Builder.Default
    private Boolean active = true;
    @Column(nullable = false)
    private Float score;
    @Builder.Default
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Builder.Default
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
    @Column(nullable = false)
    private String content;
    @ManyToOne()
    @JoinColumn(name = "quizId", nullable = false)
    private Quiz quiz;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @OneToMany(mappedBy = "question")
    private List<TakeAnswer> takeAnswers;
    private String imgURI;
}
