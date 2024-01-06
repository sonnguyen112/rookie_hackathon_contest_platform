package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserSubmitAnswerResponse {
    private int totalQuestions;
    private int correctQuestions;
    private Float score;
    List<UserSubmitAnswerListResultResponse> listResult;
}
