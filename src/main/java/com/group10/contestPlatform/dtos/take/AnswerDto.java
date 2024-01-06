package com.group10.contestPlatform.dtos.take;

import com.group10.contestPlatform.entities.Answer;
import lombok.*;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private Long id;
    private Long quizId;
    private Long questionId;
    private Boolean active;
    private Boolean correct;
    private String content;

    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.quizId = answer.getQuiz().getId();
        this.questionId = answer.getQuestion().getId();
        this.active = answer.getActive();
        this.correct = answer.getCorrect();
        this.content = answer.getContent();
    }


}
