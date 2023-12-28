package com.group10.contestPlatform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group10.contestPlatform.dtos.CreateQuizRequest;
import com.group10.contestPlatform.dtos.CreateQuizResponse;
import com.group10.contestPlatform.services.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/quiz")
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/create_quiz")
    public ResponseEntity<CreateQuizResponse> createQuiz(CreateQuizRequest createQuizRequest) {
        return null;
    }
}
