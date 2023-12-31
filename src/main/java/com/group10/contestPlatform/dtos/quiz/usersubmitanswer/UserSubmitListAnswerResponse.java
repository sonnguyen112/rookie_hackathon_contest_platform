package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSubmitListAnswerResponse {
    private long id;
    private String answerText;
    private boolean correct;
}
