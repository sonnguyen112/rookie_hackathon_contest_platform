package com.group10.contestPlatform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group10.contestPlatform.entities.Question;
import com.group10.contestPlatform.entities.Quiz;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuiz(Quiz quiz);
    List<Question> findByQuizId(Long quizId);

}
