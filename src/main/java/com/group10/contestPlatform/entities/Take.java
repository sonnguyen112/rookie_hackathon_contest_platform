package com.group10.contestPlatform.entities;

import java.sql.Timestamp;
import java.util.List;

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
@Table(name = "take")
public class Take {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "quizId", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
    private Float score;

    @Builder.Default
    private Boolean pushlished = true;

    @Builder.Default
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private Timestamp startedAt;

    @Column(nullable = false)
    private Timestamp finishedAt;

    @OneToMany(mappedBy = "take")
    private List<TakeAnswer> takeAnswers;

    @Builder.Default
    private Boolean cheat = false;

    @OneToMany(mappedBy = "take")
    private List<CheatInfo> cheatInfos;
}
