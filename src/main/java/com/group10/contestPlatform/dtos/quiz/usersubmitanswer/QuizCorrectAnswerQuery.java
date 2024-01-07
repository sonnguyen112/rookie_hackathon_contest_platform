package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
// using for quiz_answer table
public class QuizCorrectAnswerQuery {
    private Long id;
    private String content;
    private Boolean correct;

    public QuizCorrectAnswerQuery(Long id, String content, Boolean correct) {
        this.id = id;
        this.content = content;
        this.correct = correct;
    }
}
