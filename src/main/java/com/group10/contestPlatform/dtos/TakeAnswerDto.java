package com.group10.contestPlatform.dtos;

import com.group10.contestPlatform.entities.TakeAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeAnswerDto {
    private Long id;
    private Long takeId;
    private Long questionId;
    private Long answerId;
    private Boolean active;
    private String content;


    public TakeAnswerDto(TakeAnswer takeAnswer) {
        this.id = takeAnswer.getId();
        this.takeId = takeAnswer.getTake().getId();
        this.questionId = takeAnswer.getQuestion().getId();
        this.answerId = takeAnswer.getAnswer().getId();
        this.active = takeAnswer.getActive();
        this.content = takeAnswer.getContent();
    }
}