package com.group10.contestPlatform.services.imlp;

import com.amazonaws.services.kms.model.NotFoundException;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.*;
import com.group10.contestPlatform.entities.Question;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.exceptions.DataNotFoundException;
import com.group10.contestPlatform.exceptions.LocalizationUtils;
import com.group10.contestPlatform.repositories.ISubmitAnswerRepository;
import com.group10.contestPlatform.repositories.UserRepository;
import com.group10.contestPlatform.services.ISubmitAnswerService;
import com.group10.contestPlatform.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmitAnswerService implements ISubmitAnswerService {
    @Autowired
    private ISubmitAnswerRepository submitAnswerRepository;
    private final UserRepository userRepository;
    private Float score = 0.0f;
    private int correctQuestions = 0;

    @Override
    public List<QuizQuestionQuery> findQuizQuestionsByQuizId(long quizId) {
        return submitAnswerRepository.findByQuizId(quizId);
    }

    @Override
    public QuizAnswerQuery findAnswerById(long id) {
        return submitAnswerRepository.findById(id);
    }

    // this method must run after calScore method to have the score
    @Override
    public void saveToTakeAnswerTable(long quizId, long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            // get user taking the quiz
            User testUser = userRepository.findByUsername(currentPrincipalName).orElse(null);

            Take newTake = new Take();
            newTake.setCreatedAt(null);
            newTake.setStartedAt(new Timestamp(System.currentTimeMillis()));
            newTake.setFinishedAt(new Timestamp(System.currentTimeMillis()));
            newTake.setPushlished(true);
            newTake.setScore(score);


        } catch (Exception e) {

        }
    }

    @Override
    public void calScore(List<UserSubmitAnswerRequest> submitAnswerRequests, List<QuizQuestionQuery> questionQueries) {
        score = 0.0f;
        correctQuestions = 0;
        //Iterate through each submitAnswerRequest to check if the answer is correct, if correct, add score to the user
        for (UserSubmitAnswerRequest submit : submitAnswerRequests) {
            QuizAnswerQuery answerQuery = findAnswerById(submit.getSelectedAnswer());

            if (answerQuery != null && answerQuery.getCorrect()) {
                QuizQuestionQuery quizQuestionQuery = findQuizInListById(questionQueries, submit.getQuizQuestion());
                if (quizQuestionQuery != null) {
                    score += quizQuestionQuery.getScore();
                    correctQuestions++;
                }
            }
        }
    }

    @Override
    public UserSubmitAnswerResponse response (List<UserSubmitAnswerRequest> submitAnswerRequests, long quizId)
            throws DataNotFoundException {
        List<QuizQuestionQuery> questionQueries = findQuizQuestionsByQuizId(quizId);

        // Check if questionQueries is null
        if (questionQueries.size() == 0) {
            // Return a message indicating that service does not found the question in quiz_question table
            throw new DataNotFoundException("Error: Does not found any question in this Quiz");
        }

        // calculate the score
        calScore(submitAnswerRequests, questionQueries);

        // set the totalQuestions, correctQuestions, score and init a list of listResult
        UserSubmitAnswerResponse newResponse = new UserSubmitAnswerResponse();
        newResponse.setTotalQuestions(submitAnswerRequests.size());
        newResponse.setCorrectQuestions(correctQuestions);
        newResponse.setScore(score);

        // init listResult
        List<UserSubmitAnswerListResultResponse> listResultResponses = new ArrayList<>();

        // Iterate through each submitAnswerRequest to retrieve information and save it into the listResultResponses.
        for (UserSubmitAnswerRequest request : submitAnswerRequests) {
            // init new result
            UserSubmitAnswerListResultResponse newResultResponse = new UserSubmitAnswerListResultResponse();

            // find quiz_question by id
            QuizQuestionQuery quizQuestionQuery = findQuizInListById(questionQueries, request.getQuizQuestion());

            if (quizQuestionQuery == null) {
                throw new DataNotFoundException("Error: Does not found quiz question in question list");
            }

            newResultResponse.setId(request.getQuizQuestion());
            newResultResponse.setImage(quizQuestionQuery.getImgurl());
            newResultResponse.setText(quizQuestionQuery.getContent());

            // init new answer
            UserSubmitListAnswerResponse newAnswerResponse = new UserSubmitListAnswerResponse();
            // init new list answers
            List<UserSubmitListAnswerResponse> newListAnswerResponses = new ArrayList<>();

            // query answer by id
            QuizAnswerQuery answerQuery = findAnswerById(request.getSelectedAnswer());

            if (answerQuery == null) {
                throw new DataNotFoundException("Error: Does not found quiz answer");
            }

            newAnswerResponse.setId(request.getSelectedAnswer());
            newAnswerResponse.setAnswerText(answerQuery.getContent());
            newAnswerResponse.setCorrect(answerQuery.getCorrect());

            // add answer to list listResult
            newListAnswerResponses.add(newAnswerResponse);

            // add list answers to result
            newResultResponse.setAnswers(newListAnswerResponses);

            // add result to listResult
            listResultResponses.add(newResultResponse);
        }

        // add listResult to response
        newResponse.setListResult(listResultResponses);

        return newResponse;
    }

    public QuizQuestionQuery findQuizInListById (List<QuizQuestionQuery> questionQueries, long id) {
        for (QuizQuestionQuery quizQuestionQuery : questionQueries) {
            if (quizQuestionQuery.getId() == id) {
                return quizQuestionQuery;
            }
        }

        return null;
    }
}
