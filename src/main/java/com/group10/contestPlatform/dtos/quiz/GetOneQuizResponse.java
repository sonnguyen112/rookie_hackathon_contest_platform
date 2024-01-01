package com.group10.contestPlatform.dtos.quiz;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOneQuizResponse {
    private String title;
    private String description;
    private String imageQuizUrl;
    private Long startAt;
    private Long endAt;
    private List<GetOneQuestionResponse> questions;
    private List<GetOneAnswerResponse> answers;
}
