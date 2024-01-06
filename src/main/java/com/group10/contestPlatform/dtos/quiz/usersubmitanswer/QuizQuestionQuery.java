package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// using for quiz_question table
@Getter @Setter
public class QuizQuestionQuery {
    private Long id;
    private String imgurl;
    private String content;
    private Float score;

    public QuizQuestionQuery(Long id, String imgurl, String content, Float score) {
        this.id = id;
        this.imgurl = imgurl;
        this.content = content;
        this.score = score;
    }
}
