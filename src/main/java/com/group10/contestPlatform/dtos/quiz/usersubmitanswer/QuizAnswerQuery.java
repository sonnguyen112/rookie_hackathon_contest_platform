package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
// using for quiz_answer table
public class QuizAnswerQuery {
    private String content;
    private Boolean correct;

    public QuizAnswerQuery(String content, Boolean correct) {
        this.content = content;
        this.correct = correct;
    }
}
