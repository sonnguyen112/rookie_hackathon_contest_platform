package com.group10.contestPlatform.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group10.contestPlatform.dtos.quiz.CheckCheatRequest;
import com.group10.contestPlatform.dtos.quiz.CheckCheatResponse;
import com.group10.contestPlatform.dtos.quiz.CreateQuizRequest;
import com.group10.contestPlatform.dtos.quiz.CreateQuizResponse;
import com.group10.contestPlatform.dtos.quiz.DeleteQuizResponse;
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

    @PutMapping("/update_quiz/{slug}")
    public ResponseEntity<CreateQuizResponse> updateQuiz(@RequestBody CreateQuizRequest updateQuizRequest, @PathVariable String slug) {
        quizService.updateQuiz(updateQuizRequest, slug);
        return ResponseEntity.status(200)
                .body(CreateQuizResponse.builder().message("Quiz created successfully").build());
    }

    @DeleteMapping("/delete_one_quiz/{slug}")
    public ResponseEntity<DeleteQuizResponse> deleteQuiz(@PathVariable String slug) {
        quizService.deleteQuiz(slug);
        return ResponseEntity.status(200).body(DeleteQuizResponse.builder().message("Quiz deleted successfully").build());
    }

    @PostMapping("/check_cheat")
    public ResponseEntity<CheckCheatResponse> checkCheat(@RequestBody CheckCheatRequest checkCheatRequest) {
        return ResponseEntity.status(200).body(quizService.checkCheat(checkCheatRequest));
    }
}
