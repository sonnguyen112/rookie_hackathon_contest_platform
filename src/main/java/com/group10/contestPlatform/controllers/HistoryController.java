package com.group10.contestPlatform.controllers;

import com.group10.contestPlatform.dtos.quiz.GetQuestionResponse;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.ResponseResultTake;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.SubmitAnswerRequest;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerResponse;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.repositories.SubmitAnswerRepository;
import com.group10.contestPlatform.services.HistoryService;
import com.group10.contestPlatform.services.TakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.group10.contestPlatform.dtos.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/history")
public class HistoryController {
    private final HistoryService takeService;


    @GetMapping("/get_by_user")
    public ResponseEntity<List<HistoryDto>> getAll(){
        List<Take> list = takeService.getAllByUser();
        return ResponseEntity.status(200)
                .body(list.stream().map(i ->
                        new HistoryDto(i)
                ).toList());
    }

    @GetMapping("/get_by_quiz/{quizId}")
    public ResponseEntity<HistoryDto> getByQuiz(@PathVariable Long quizId){
        Optional<Take> take = takeService.getTakeByQuiz(quizId);
        return ResponseEntity.status(200)
                .body(new HistoryDto(take.get()));
    }

    @GetMapping("/do-quiz_history")
    public ResponseEntity<List<GetQuestionResponse>> joinQuizHistory(@RequestParam Long quizId){
        List<GetQuestionResponse> questionList = takeService.joinQuizHistory(quizId);
        return ResponseEntity.ok(questionList);
    }

        @GetMapping("/get_result_by_quiz/{quizId}")
    public ResponseEntity<?> submitQuizAnswers(@PathVariable long quizId
                                               ) {

        try {
            ResponseResultTake response = takeService.historyQuestionAnswer(quizId);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
