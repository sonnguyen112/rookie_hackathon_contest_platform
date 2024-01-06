package com.group10.contestPlatform.services;

import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizAnswerQuery;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizQuestionQuery;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerRequest;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.UserSubmitAnswerResponse;
import com.group10.contestPlatform.entities.Question;
import com.group10.contestPlatform.exceptions.DataNotFoundException;

import java.util.List;

public interface ISubmitAnswerService {
    // find all quiz questions by quiz_id in quiz_question table using JPA
    List<QuizQuestionQuery> findQuizQuestionsByQuizId(long id);

    // find quiz_answer which user chosen
    QuizAnswerQuery findAnswerById(long id);

    // after having question and answer, save to take_answer table
    void saveToTakeAnswerTable(long quizId, long id);

    // using JPA to query correct in quiz_answer to check if correct is true or not,
    // if true increase the score of the user
    void calScore(List<UserSubmitAnswerRequest> submitAnswerRequests, List<QuizQuestionQuery> questionQueries);

    // generate response
    UserSubmitAnswerResponse response (List<UserSubmitAnswerRequest> submitAnswerRequests, long quizId) throws DataNotFoundException;
}
