package com.group10.contestPlatform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group10.contestPlatform.dtos.quiz.CreateQuizRequest;
import com.group10.contestPlatform.dtos.quiz.CreateQuizResponse;
import com.group10.contestPlatform.services.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/quiz")
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/create_quiz")
    public ResponseEntity<CreateQuizResponse> createQuiz(@RequestBody CreateQuizRequest createQuizRequest) {
        quizService.createQuiz(createQuizRequest);
        return ResponseEntity.status(201).body(CreateQuizResponse.builder().message("Quiz created successfully").build());
    }
}
