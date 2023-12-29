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
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hostId", nullable = false)
    private User host;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String slug;
    @Builder.Default
    private Boolean published = true;
    @Builder.Default
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Builder.Default
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
    @Builder.Default
    private Timestamp publishedAt = new Timestamp(System.currentTimeMillis());
    @Column(nullable = false)
    private Timestamp startAt;
    @Column(nullable = false)
    private Timestamp endAt;
    private String content;
    @OneToMany(mappedBy = "quiz")
    private Set<Question> questions;
    // @OneToMany(mappedBy = "quiz")
    // private Set<Answer> answers;
    @OneToMany(mappedBy = "quiz")
    private Set<Take> takes;
    private String imgURI;
}
