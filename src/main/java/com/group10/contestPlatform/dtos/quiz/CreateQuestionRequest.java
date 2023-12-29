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
public class CreateQuestionRequest {
    private String name;
    private Float point;
    private String imageQuestionUrl;
    private List<CreateAnswerRequest> options;
}
