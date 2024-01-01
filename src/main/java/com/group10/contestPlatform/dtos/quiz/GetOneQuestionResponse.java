package com.group10.contestPlatform.dtos.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOneQuestionResponse {
    private Long id;
    private String description;
    private String image_question_url;
    private Float score;
}
