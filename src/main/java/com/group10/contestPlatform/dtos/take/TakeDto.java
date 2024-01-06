package com.group10.contestPlatform.dtos.take;

import com.group10.contestPlatform.dtos.take.TakeAnswerDto;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.entities.TakeAnswer;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TakeDto {
    private Long id;
    private Long userId;
    private Long quizId;
    private Float score;
    private Boolean pushlished;
    private List<TakeAnswerDto> takeAnswers = new ArrayList<>();

    public TakeDto(Take take) {
        this.id = take.getId();
        this.userId = take.getUser().getId();
        this.quizId = take.getQuiz().getId();
        this.score = take.getScore();
        this.pushlished = take.getPushlished();

        for(TakeAnswer takeAnswer: take.getTakeAnswers()){
            takeAnswers.add(new TakeAnswerDto(takeAnswer));
        }
    }
}
