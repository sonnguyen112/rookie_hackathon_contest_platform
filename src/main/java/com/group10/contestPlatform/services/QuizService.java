package com.group10.contestPlatform.services;

import org.springframework.stereotype.Service;

import com.group10.contestPlatform.dtos.quiz.CreateQuizRequest;
import com.group10.contestPlatform.entities.Quiz;
import com.group10.contestPlatform.repositories.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public Quiz createQuiz(CreateQuizRequest createQuizRequest) {
        return null;
    }
    
}
