package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSubmitAnswerRequest {
    private int quizQuestion;
    private int selectedAnswer;
}
