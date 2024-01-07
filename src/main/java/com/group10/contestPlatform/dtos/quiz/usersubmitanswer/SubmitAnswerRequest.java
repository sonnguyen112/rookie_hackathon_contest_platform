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
public class SubmitAnswerRequest {
    List<String> imageCheated;
    List<UserSubmitAnswerRequest> answers;

}
