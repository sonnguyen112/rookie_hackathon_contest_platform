package com.group10.contestPlatform.services;

import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizQuestionQuery;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerRequest;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerResponse;
import com.group10.contestPlatform.exceptions.DataNotFoundException;

import java.util.List;

public interface ISubmitAnswerService {
    // after having question and answer, save to take_answer table
    void saveToTakeAnswerTable(List<UserSubmitAnswerRequest> submitAnswerRequests, long quizId);

    // using JPA to query correct in quiz_answer to check if correct is true or not,
    // if true increase the score of the user
    void calScore(UserSubmitAnswerRequest requests, List<QuizQuestionQuery> questionQueries);

    // generate response
    UserSubmitAnswerResponse response (List<UserSubmitAnswerRequest> submitAnswerRequests, long quizId) throws DataNotFoundException;
}
