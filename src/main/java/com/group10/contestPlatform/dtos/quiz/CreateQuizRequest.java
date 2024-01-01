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
public class CreateQuizRequest {
    private String title;
    private String description;
    private String imageQuizUrl;
    private String startAt;
    private String endAt;
    private List<CreateQuestionRequest> questions;
}
