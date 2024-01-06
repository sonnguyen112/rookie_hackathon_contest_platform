package com.group10.contestPlatform.controllers;

import com.amazonaws.services.apigateway.model.NotFoundException;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizQuestionQuery;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerRequest;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerResponse;
import com.group10.contestPlatform.entities.Question;
import com.group10.contestPlatform.exceptions.DataNotFoundException;
import com.group10.contestPlatform.services.ISubmitAnswerService;
import com.group10.contestPlatform.services.imlp.SubmitAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
@RequiredArgsConstructor
public class UserSubmitAnswerController {
    private final SubmitAnswerService submitAnswerService;

    @PostMapping("/{quizId}")
    public ResponseEntity<?> submitQuizAnswers(@PathVariable long quizId,
                                                   @RequestBody List<UserSubmitAnswerRequest> submitAnswerRequests) {

        try {
            UserSubmitAnswerResponse response = submitAnswerService.response(submitAnswerRequests, quizId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
