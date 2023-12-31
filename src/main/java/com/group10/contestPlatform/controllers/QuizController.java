package com.group10.contestPlatform.controllers;

import java.util.List;
import java.util.Map;

import com.group10.contestPlatform.dtos.quiz.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .id(quiz.getId())
                .duration(quiz.getEndAt().getTime() - quiz.getStartAt().getTime())
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
                                        .answerText(answer.getContent())
                                        .correct(answer.getCorrect()).build())
                                .toList())
                        .build()

        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Quiz>> searchQuiz(@RequestParam String name, @RequestParam String dateStart,
                                                 @RequestParam String endStart){

        List<Quiz> quizList = quizService.searchQuiz(name, dateStart, endStart);
        return ResponseEntity.ok(quizList);
    }

    @GetMapping("/do-quiz")
    public ResponseEntity<List<GetQuestionResponse>> joinQuiz(@RequestParam Long quizId){
        List<GetQuestionResponse> questionList = quizService.joinQuiz(quizId);
        return ResponseEntity.ok(questionList);
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
