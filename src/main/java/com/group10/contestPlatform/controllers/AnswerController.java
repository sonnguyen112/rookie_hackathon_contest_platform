package com.group10.contestPlatform.controllers;

import com.group10.contestPlatform.dtos.take.AnswerDto;
import com.group10.contestPlatform.entities.Answer;
import com.group10.contestPlatform.services.imlp.AnswerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/answer")
public class AnswerController {
    private final AnswerServiceImpl answerService;

    @GetMapping("")
    public ResponseEntity<List<AnswerDto>> getAll(){
        List<Answer> list = answerService.getAll();
        return ResponseEntity.status(200).body(list.stream().map(ans -> AnswerDto.builder()
                .id(ans.getId()).quizId(ans.getQuiz().getId()).questionId(ans.getQuestion().getId())
                .active(ans.getActive())
                .correct(ans.getCorrect())
                .content(ans.getContent())
                .build()).toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AnswerDto> get(@PathVariable Long id){
        Optional<Answer> answer = answerService.get(id);
        if(answer == null){
            return ResponseEntity.status(200).body(null);
        }else {
            AnswerDto result = new AnswerDto(answer.get());
            return ResponseEntity.status(200).body(result);
        }

    }
}
