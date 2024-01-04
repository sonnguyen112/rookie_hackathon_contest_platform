package com.group10.contestPlatform.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group10.contestPlatform.dtos.quiz.CreateQuizRequest;
import com.group10.contestPlatform.dtos.quiz.CreateQuizResponse;
import com.group10.contestPlatform.dtos.quiz.GetOneAnswerResponse;
import com.group10.contestPlatform.dtos.quiz.GetOneQuestionResponse;
import com.group10.contestPlatform.dtos.quiz.GetOneQuizResponse;
import com.group10.contestPlatform.dtos.quiz.GetQuizResponse;
import com.group10.contestPlatform.entities.Quiz;
import com.group10.contestPlatform.services.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/quiz")
//@PreAuthorize("hasAuthority('ADMIN')")
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/create_quiz")
    public ResponseEntity<CreateQuizResponse> createQuiz(@RequestBody CreateQuizRequest createQuizRequest) {
        quizService.createQuiz(createQuizRequest);
        return ResponseEntity.status(201)
                .body(CreateQuizResponse.builder().message("Quiz created successfully").build());
    }

    @GetMapping("/get_all_quiz")
    public ResponseEntity<List<GetQuizResponse>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.status(200).body(quizzes.stream().map(quiz -> GetQuizResponse.builder()
                .title(quiz.getTitle()).avatar(quiz.getImgURI()).slug(quiz.getSlug()).build()).toList());
    }

    @GetMapping("/get_one_quiz/{slug}")
    public ResponseEntity<GetOneQuizResponse> getOneQuiz(@PathVariable String slug) {
        Quiz quiz = quizService.getOneQuiz(slug);
        // System.out.println(quiz);
        return ResponseEntity.status(200).body(
                GetOneQuizResponse.builder().title(quiz.getTitle())
                        .description(quiz.getContent())
                        .imageQuizUrl(quiz.getImgURI())
                        .startAt(quiz.getStartAt().getTime())
                        .endAt(quiz.getEndAt().getTime())
                        .questions(quiz.getQuestions().stream()
                                .map(question -> GetOneQuestionResponse.builder().id(question.getId())
                                        .description(question.getContent())
                                        .image_question_url(question.getImgURI())
                                        .score(question.getScore()).build())
                                .toList())
                        .answers(quiz.getAnswers().stream()
                                .map(answer -> GetOneAnswerResponse.builder().id(answer.getQuestion().getId())
                                        .content(answer.getContent())
                                        .is_true(answer.getCorrect()).build())
                                .toList())
                        .build()

        );
    }
}
