package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;


import com.group10.contestPlatform.entities.Answer;
import com.group10.contestPlatform.entities.Question;
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

    public QuizCorrectAnswerQuery(Answer answer) {
        this.id = answer.getQuestion().getId();
        this.content = answer.getContent();
        this.correct = answer.getCorrect();
    }

    @Override
    public String toString() {
        return "QuizCorrectAnswerQuery{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", correct=" + correct +
                '}';
    }
}
