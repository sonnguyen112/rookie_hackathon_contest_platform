package com.group10.contestPlatform.dtos.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOneAnswerResponse {
    private Long id; // Question id
    private String content;
    private Boolean is_true;
}
