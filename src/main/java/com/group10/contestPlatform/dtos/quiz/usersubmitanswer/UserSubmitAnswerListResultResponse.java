package com.group10.contestPlatform.dtos.quiz.usersubmitanswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSubmitAnswerListResultResponse {
    private long id;
    private String image;
    private String text;
    List<UserSubmitListAnswerResponse> answers;
}
