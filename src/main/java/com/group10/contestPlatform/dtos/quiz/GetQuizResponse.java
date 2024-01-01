package com.group10.contestPlatform.dtos.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetQuizResponse {
    private String title;
    private String avatar;
    private String slug;
}
