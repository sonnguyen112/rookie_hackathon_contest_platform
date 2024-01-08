package com.group10.contestPlatform.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "take_answer")
public class TakeAnswer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "takeId", nullable = false)
    private Take take;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answerId")
    private Answer answer;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Builder.Default
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    private String content;
}
