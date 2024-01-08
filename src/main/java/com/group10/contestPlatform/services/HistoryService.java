package com.group10.contestPlatform.services;

import com.group10.contestPlatform.dtos.quiz.GetOneAnswerResponse;
import com.group10.contestPlatform.dtos.quiz.GetQuestionResponse;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.*;
import com.group10.contestPlatform.entities.*;
import com.group10.contestPlatform.exceptions.AppException;
import com.group10.contestPlatform.exceptions.DataNotFoundException;
import com.group10.contestPlatform.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final TakeRepository takeRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final TakeAnswerRepository takeAnswerRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final SubmitAnswerRepository submitAnswerRepository;


    public Optional<Take> getTakeByQuiz(Long quizId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            User curUser = userRepository.findByUsername(currentPrincipalName).orElse(null);
            Quiz quiz = quizRepository.getReferenceById(quizId);

            return takeRepository.findTakesByQuizAndUser(quiz.getId(), curUser.getId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    public List<Take> getAllByUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            User curUser = userRepository.findByUsername(currentPrincipalName).orElse(null);
            return takeRepository.findByUserId(curUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    public List<GetQuestionResponse> joinQuizHistory(Long quizId) {
        List<GetQuestionResponse> questionResponseList = new ArrayList<>();

        if (Objects.nonNull(quizId)) {
            List<Question> questions = questionRepository.findByQuizId(quizId);
            Quiz quiz = quizRepository.findById(quizId).orElse(null);
//            Long nowTimestamp = new Timestamp(System.currentTimeMillis()).getTime();

//            if (Objects.isNull(quiz)) {
//                throw new AppException(400, 3);
//            }

//            if (nowTimestamp < quiz.getStartAt().getTime() || nowTimestamp > quiz.getEndAt().getTime()) {
//                throw new AppException(400, 14);
//            }

            // Take take = takeRepository.findByQuiz(quiz);

            if (!CollectionUtils.isEmpty(questions)) {
                for (Question question : questions) {
                    GetQuestionResponse questionResponse = new GetQuestionResponse();
                    List<GetOneAnswerResponse> answerResponses = new ArrayList<>();

                    questionResponse.setId(question.getId());
                    questionResponse.setText(question.getContent());
                    questionResponse.setImage(question.getImgURI());

                    List<Answer> answerList = answerRepository.findByQuestionId(question.getId());
                    if (!CollectionUtils.isEmpty(answerList)) {
                        for (Answer answer : answerList) {
                            GetOneAnswerResponse answerResponse = new GetOneAnswerResponse();
                            answerResponse.setId(answer.getId());
                            answerResponse.setAnswerText(answer.getContent());
                            answerResponse.setCorrect(answer.getCorrect());

                            answerResponses.add(answerResponse);
                        }
                    }

                    questionResponse.setAnswers(answerResponses);
                    questionResponseList.add(questionResponse);
                }
            }
        }

        return questionResponseList;
    }


    public ResponseResultTake historyQuestionAnswer (long quizId)
            throws DataNotFoundException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            User curUser = userRepository.findByUsername(currentPrincipalName).orElse(null);
            List<Question> questionQuiz =  questionRepository.findByQuizId(quizId);
            Optional<Take> take = takeRepository.findTakesByQuizAndUser(quizId, curUser.getId());


            if (questionQuiz.size() == 0) {
                throw new DataNotFoundException("Error: Does not found any question in this Quiz");
            }
            ResponseResultTake newResponse = new ResponseResultTake();

            newResponse.setTotalQuestions(questionQuiz.size());

            // init listResult
            List<ResponseAnswer> listResultResponses = new ArrayList<>();
            List<ResponseAnswer> listResultTakeResponse = new ArrayList<>();

            float score = 0.0f;
            int correctQuestions = 0;
            for (TakeAnswer takeAnswer : take.get().getTakeAnswers()) {
                Question question = questionRepository.getReferenceById(takeAnswer.getQuestion().getId());

                ResponseAnswer newResultCorrectResponse = new ResponseAnswer();

                newResultCorrectResponse.setId(question.getId());
                newResultCorrectResponse.setImage(question.getImgURI());
                newResultCorrectResponse.setText(question.getContent());

                UserSubmitListAnswerResponse newAnswerResponse = new UserSubmitListAnswerResponse();
                QuizCorrectAnswerQuery answerQuery = findAnswerById(question.getId());

                for(Answer answer: question.getAnswers()){
                    if(takeAnswer.getAnswer().getId() == answer.getId() && answer.getCorrect()){
                        correctQuestions++;
                    }
                }
                newAnswerResponse.setId(answerQuery.getId());
                newAnswerResponse.setAnswerText(answerQuery.getContent());
                newAnswerResponse.setCorrect(answerQuery.getCorrect());

                newResultCorrectResponse.setAnswers(newAnswerResponse);


                ResponseAnswer resultTakeResponse = new ResponseAnswer();
                UserSubmitListAnswerResponse resultTakeAnswerResponse = new UserSubmitListAnswerResponse();

                Answer answer = takeAnswer.getAnswer();

                resultTakeAnswerResponse.setId(answer.getId());
                resultTakeAnswerResponse.setAnswerText(answer.getContent());
                resultTakeAnswerResponse.setCorrect(answer.getCorrect());

                resultTakeResponse.setId(question.getId());
                resultTakeResponse.setImage(question.getImgURI());
                resultTakeResponse.setText(question.getContent());
                resultTakeResponse.setAnswers(resultTakeAnswerResponse);



                // add result to listResult
                listResultResponses.add(newResultCorrectResponse);
                listResultTakeResponse.add(resultTakeResponse);
            }

            newResponse.setListAnswerCorrect(listResultResponses);
            newResponse.setListAnswerTake(listResultTakeResponse);
            newResponse.setScore(take.get().getScore());
            newResponse.setCorrectQuestions(correctQuestions);

            return newResponse;

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }

    }

    public QuizCorrectAnswerQuery findAnswerById(long id) {
        return submitAnswerRepository.findById(id);
    }


}
