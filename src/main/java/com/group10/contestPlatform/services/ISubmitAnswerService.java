package com.group10.contestPlatform.services;

import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizQuestionQuery;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerRequest;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerResponse;
import com.group10.contestPlatform.exceptions.DataNotFoundException;

import java.util.List;

public interface ISubmitAnswerService {

    void calScore(Long correctAnswerId, UserSubmitAnswerRequest requests, List<QuizQuestionQuery> questionQueries);

    // generate response
    UserSubmitAnswerResponse response (List<UserSubmitAnswerRequest> submitAnswerRequests, long quizId) throws DataNotFoundException;


}
