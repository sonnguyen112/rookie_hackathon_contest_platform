package com.group10.contestPlatform.services.imlp;

import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.*;
import com.group10.contestPlatform.entities.*;
import com.group10.contestPlatform.exceptions.AppException;
import com.group10.contestPlatform.exceptions.DataNotFoundException;
import com.group10.contestPlatform.repositories.*;
import com.group10.contestPlatform.services.AmazonClient;
import com.group10.contestPlatform.services.ISubmitAnswerService;
import com.group10.contestPlatform.utils.StorageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubmitAnswerService implements ISubmitAnswerService {
    @Autowired
    private SubmitAnswerRepository submitAnswerRepository;
    private final AmazonClient amazonClient;
    private final UserRepository userRepository; // for finding user by id
    private final QuizRepository quizRepository; // for finding quiz by id
    private final TakeRepository takeRepository; // for saving to take table
    private final AnswerRepository quizAnswerRepository; // for finding quiz_answer by id
    private final QuestionRepository quizQuestionRepository; // for finding quiz_question by id
    private final TakeAnswerRepository takeAnswerRepository; // for saving to take_answer table
    private final CheatRepository cheatRepository;
    private Float score = 0.0f;
    private int correctQuestions = 0;


    // method

    public List<QuizQuestionQuery> findQuizQuestionsByQuizId(long quizId) {
        return submitAnswerRepository.findByQuizId(quizId);
    }


    public QuizCorrectAnswerQuery findAnswerById(long id) {
        return submitAnswerRepository.findById(id);
    }

    // this method must run after calScore method to have the score

    public void saveToTakeAnswerTable(List<UserSubmitAnswerRequest> submitAnswerRequests,  List<String> imageCheated,long quizId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            User testUser = userRepository.findByUsername(currentPrincipalName).orElse(null);
            Take newTake = new Take();
            newTake.setCreatedAt(null);
            newTake.setStartedAt(new Timestamp(System.currentTimeMillis()));
            newTake.setFinishedAt(new Timestamp(System.currentTimeMillis()));
            newTake.setCheat(!imageCheated.isEmpty());

            newTake.setPushlished(true);
            newTake.setScore(score);
            newTake.setUser(testUser);

            // find quiz by id and add to newTake
            Optional<Quiz> quizOptional = quizRepository.findById(quizId);
            if (quizOptional.isPresent()) {
                Quiz quiz = quizOptional.get();
                newTake.setQuiz(quiz);

            }

            takeRepository.save(newTake);
            if (!imageCheated.isEmpty()) {
                for (String img : imageCheated) {
                    CheatInfo ct = new CheatInfo();
                    ct.setTake(newTake);
                    ct.setImgUrl(amazonClient.uploadFile(img));
                    cheatRepository.save(ct);
                }
            }
            for (UserSubmitAnswerRequest request : submitAnswerRequests) {
                TakeAnswer newTakeAnswer = new TakeAnswer();
                Optional<Question> quizQuestionOptional = quizQuestionRepository.findById(request.getQuizQuestion());
                Optional<Answer> quizAnswerOptional = quizAnswerRepository.findById(request.getSelectedAnswer());

                // check if quizQuestion or quizAnswer null or not
                if (quizQuestionOptional.isPresent()) {
                    Question quizQuestion = quizQuestionOptional.get();
                    newTakeAnswer.setQuestion(quizQuestion);
                }
                if (quizAnswerOptional.isPresent()) {
                    Answer quizAnswer = quizAnswerOptional.get();
                    newTakeAnswer.setContent(quizAnswer.getContent());
                    newTakeAnswer.setAnswer(quizAnswer);
                }
                newTakeAnswer.setTake(newTake);
                takeAnswerRepository.save(newTakeAnswer);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }



    @Override
    public void calScore(Long correctAnswerId, UserSubmitAnswerRequest request, List<QuizQuestionQuery> questionQueries) {
            if (correctAnswerId == request.getSelectedAnswer()) {
                QuizQuestionQuery quizQuestionQuery = findQuizInListById(questionQueries, request.getQuizQuestion());
                if (quizQuestionQuery != null) {
                    score += quizQuestionQuery.getScore();
                    correctQuestions++;
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

        // set the totalQuestions, correctQuestions, score and init a list of listResult
        UserSubmitAnswerResponse newResponse = new UserSubmitAnswerResponse();
        newResponse.setTotalQuestions(submitAnswerRequests.size());
        // add listResult to response
        newResponse.setListResult(takeListResult(submitAnswerRequests, questionQueries));
        newResponse.setScore(score);
        newResponse.setCorrectQuestions(correctQuestions);

        return newResponse;
    }



    public List<UserSubmitAnswerListResultResponse> takeListResult (List<UserSubmitAnswerRequest> submitAnswerRequests,
                                                                    List<QuizQuestionQuery> questionQueries)
                                                                    throws DataNotFoundException {
        score = 0.0f;
        correctQuestions = 0;

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

            // query answer by question id and true correct
            QuizCorrectAnswerQuery answerQuery = findAnswerById(request.getQuizQuestion());

            if (answerQuery == null) {
                throw new DataNotFoundException("Error: Does not found quiz answer");
            }

            // calculate the score
            calScore(answerQuery.getId(), request, questionQueries);

            newAnswerResponse.setId(answerQuery.getId());
            newAnswerResponse.setAnswerText(answerQuery.getContent());
            newAnswerResponse.setCorrect(answerQuery.getCorrect());

            // add answer to list listResult
            newListAnswerResponses.add(newAnswerResponse);

            // add list answers to result
            newResultResponse.setAnswers(newListAnswerResponses);

            // add result to listResult
            listResultResponses.add(newResultResponse);
        }

        return listResultResponses;
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
