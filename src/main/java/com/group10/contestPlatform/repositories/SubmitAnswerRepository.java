package com.group10.contestPlatform.repositories;

import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizCorrectAnswerQuery;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizQuestionQuery;
import com.group10.contestPlatform.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Question for quiz_question table
public interface SubmitAnswerRepository extends JpaRepository<Question, Long> {
    @Query("SELECT new com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizQuestionQuery(q.id, q.imgURI,q.content, q.score) " +
            "FROM Question q WHERE q.quiz.id = :quizId")
    List<QuizQuestionQuery> findByQuizId(long quizId);

    @Query("SELECT new com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizCorrectAnswerQuery(q.id, q.content, q.correct) " +
            "FROM Answer q WHERE q.question.id = :questionId AND q.correct = true")
    QuizCorrectAnswerQuery findById(long questionId);
}
