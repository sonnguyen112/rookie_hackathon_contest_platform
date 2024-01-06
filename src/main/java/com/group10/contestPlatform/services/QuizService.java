package com.group10.contestPlatform.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.group10.contestPlatform.dtos.quiz.CheckCheatRequest;
import com.group10.contestPlatform.dtos.quiz.CheckCheatResponse;
import com.group10.contestPlatform.dtos.quiz.CreateAnswerRequest;
import com.group10.contestPlatform.dtos.quiz.CreateQuestionRequest;
import com.group10.contestPlatform.dtos.quiz.CreateQuizRequest;
import com.group10.contestPlatform.entities.Answer;
import com.group10.contestPlatform.entities.Question;
import com.group10.contestPlatform.entities.Quiz;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.exceptions.AppException;
import com.group10.contestPlatform.repositories.AnswerRepository;
import com.group10.contestPlatform.repositories.QuestionRepository;
import com.group10.contestPlatform.repositories.QuizRepository;
import com.group10.contestPlatform.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AmazonClient amazonClient;
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${urlDefautQuiz}")
    private String urlDefautQuiz;
    @Value("${fastapiUrl}")
    private String fastapiUrl;

    public void createQuiz(CreateQuizRequest createQuizRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            User testUser = userRepository.findByUsername(currentPrincipalName).orElse(null);

            Quiz newQuiz = new Quiz();
            newQuiz.setTitle(createQuizRequest.getTitle());
            newQuiz.setContent(createQuizRequest.getDescription());
            if (createQuizRequest.getImageQuizUrl() != null) {
                newQuiz.setImgURI(amazonClient.uploadFile(createQuizRequest.getImageQuizUrl()));
            } else {
                newQuiz.setImgURI(urlDefautQuiz);
            }
            newQuiz.setStartAt(new Timestamp(Long.parseLong(createQuizRequest.getStartAt())));
            newQuiz.setEndAt(new Timestamp(Long.parseLong(createQuizRequest.getEndAt())));
            newQuiz.setHost(testUser);
            newQuiz.setSlug(createQuizRequest.getTitle() + "-" + UUID.randomUUID().toString());
            quizRepository.save(newQuiz);

            for (CreateQuestionRequest question : createQuizRequest.getQuestions()) {
                Question newQuestion = new Question();
                newQuestion.setContent(question.getName());
                newQuestion.setScore(question.getPoint());
                if (question.getImageQuestionUrl() != null) {
                    newQuestion.setImgURI(amazonClient.uploadFile(question.getImageQuestionUrl()));
                } else {
                    newQuestion.setImgURI(urlDefautQuiz);
                }
                newQuestion.setQuiz(newQuiz);
                questionRepository.save(newQuestion);

                for (CreateAnswerRequest answer : question.getOptions()) {
                    Answer newAnswer = Answer.builder().content(answer.getContent()).correct(answer.getIs_true())
                            .build();
                    newAnswer.setQuestion(newQuestion);
                    newAnswer.setQuiz(newQuiz);
                    answerRepository.save(newAnswer);
                }
            }
            User curUser = userRepository.findByUsername(currentPrincipalName).orElse(null);
            createQuizUtil(createQuizRequest, curUser);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    public List<Quiz> getAllQuizzes() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            User testUser = userRepository.findByUsername(currentPrincipalName).orElse(null);
            List<Quiz> allQuizzes = quizRepository.findAllByHost(testUser);
            // System.out.println(allQuizzes);
            return allQuizzes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    public Quiz getOneQuiz(String slug) {
        Quiz quiz = quizRepository.findBySlug(slug).orElseThrow(() -> new AppException(400, 3));
        // System.out.println(quiz);
        return quiz;
    }

    @Transactional
    public void updateQuiz(CreateQuizRequest updateQuizRequest, String slug) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            User curUser = userRepository.findByUsername(currentPrincipalName).orElse(null);
            Quiz quiz = quizRepository.findBySlug(slug).orElseThrow(() -> new AppException(400, 3));
            if (quiz.getHost().getId() != curUser.getId()) {
                throw new AppException(400, 13);
            }
            quizRepository.delete(quiz);
            createQuizUtil(updateQuizRequest, curUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    private void createQuizUtil(CreateQuizRequest createQuizRequest, User curUser) {
        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(createQuizRequest.getTitle());
        newQuiz.setContent(createQuizRequest.getDescription());
        if (createQuizRequest.getImageQuizUrl() != null) {
            newQuiz.setImgURI(amazonClient.uploadFile(createQuizRequest.getImageQuizUrl()));
        } else {
            newQuiz.setImgURI(urlDefautQuiz);
        }
        newQuiz.setStartAt(new Timestamp(Long.parseLong(createQuizRequest.getStartAt())));
        newQuiz.setEndAt(new Timestamp(Long.parseLong(createQuizRequest.getEndAt())));
        newQuiz.setHost(curUser);
        newQuiz.setSlug(createQuizRequest.getTitle() + "-" + UUID.randomUUID().toString());
        quizRepository.save(newQuiz);
        for (CreateQuestionRequest question : createQuizRequest.getQuestions()) {
            Question newQuestion = new Question();
            newQuestion.setContent(question.getName());
            newQuestion.setScore(question.getPoint());
            if (question.getImageQuestionUrl() != null) {
                newQuestion.setImgURI(amazonClient.uploadFile(question.getImageQuestionUrl()));
            } else {
                newQuestion.setImgURI(urlDefautQuiz);
            }
            newQuestion.setQuiz(newQuiz);
            questionRepository.save(newQuestion);
            for (CreateAnswerRequest answer : question.getOptions()) {
                Answer newAnswer = Answer.builder().content(answer.getContent()).correct(answer.getIs_true())
                        .build();
                newAnswer.setQuestion(newQuestion);
                newAnswer.setQuiz(newQuiz);
                answerRepository.save(newAnswer);
            }
        }
    }

    @Transactional
    public void deleteQuiz(String slug) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            User curUser = userRepository.findByUsername(currentPrincipalName).orElse(null);
            Quiz quiz = quizRepository.findBySlug(slug).orElseThrow(() -> new AppException(400, 3));
            if (quiz.getHost().getId() != curUser.getId()) {
                throw new AppException(400, 13);
            }
            quizRepository.delete(quiz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    public CheckCheatResponse checkCheat(CheckCheatRequest checkCheatRequest) {
        ResponseEntity<CheckCheatResponse> response = restTemplate.postForEntity(
            fastapiUrl + "/api/v1/quiz/check_cheat", 
            checkCheatRequest,
            CheckCheatResponse.class);
        // System.out.println(response.getBody());
        return response.getBody();
    }

}
