package com.group10.contestPlatform.services.imlp;

import com.group10.contestPlatform.dtos.take.TakeAnswerDto;
import com.group10.contestPlatform.dtos.take.TakeDto;
import com.group10.contestPlatform.entities.*;
import com.group10.contestPlatform.exceptions.AppException;
import com.group10.contestPlatform.repositories.*;
import com.group10.contestPlatform.services.ITakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TakeServiceImpl implements ITakeService {
    private final TakeRepository takeRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final TakeAnswerRepository takeAnswerRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Override
    public void create(TakeDto dto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            User curUser = userRepository.findByUsername(currentPrincipalName).orElse(null);


            createUtil(dto, curUser);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    @Override
    public void createUtil(TakeDto dto, User curUser) {
        float score = 0;
        Take take = new Take();
        Optional<Quiz> quiz = quizRepository.findById(dto.getQuizId());
        take.setUser(curUser);
        take.setQuiz(quiz.get());
        take.setScore(0.0F);
        takeRepository.save(take);

        if(quiz.get().getAnswers().size() != dto.getTakeAnswers().size()){
            List<TakeAnswer> takeAnswers = new ArrayList<>();
            for(TakeAnswerDto takeAnswerDto: dto.getTakeAnswers()){
                TakeAnswer takeAnswer = new TakeAnswer();
                Optional<Question> question = questionRepository.findById(takeAnswerDto.getQuestionId());
                Optional<Answer> answer = answerRepository.findById(takeAnswerDto.getAnswerId());

                if(answer.get().getCorrect()){
                    score += question.get().getScore();
                }
                takeAnswer.setTake(take);
                takeAnswer.setQuestion(question.get());
                takeAnswer.setAnswer(answer.get());
                takeAnswer.setContent(answer.get().getContent());
                takeAnswers.add(takeAnswer);
                takeAnswerRepository.save(takeAnswer);


            }
            take.setScore(score);
            take.setTakeAnswers(takeAnswers);
            takeRepository.save(take);
        }else
        {
            throw new IllegalArgumentException("List Answer no valid");
        }


    }

    @Override
    public List<Take> getAll() {
        return takeRepository.findAll();
    }

    @Override
    public Optional<Take> get(Long id) {
        return Optional.of(takeRepository.getReferenceById(id));
    }

    @Override
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
}
