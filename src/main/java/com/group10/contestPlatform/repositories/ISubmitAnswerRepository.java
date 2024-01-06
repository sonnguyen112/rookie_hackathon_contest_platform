package com.group10.contestPlatform.repositories;

import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizAnswerQuery;
import com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizQuestionQuery;
import com.group10.contestPlatform.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Question for quiz_question table
public interface ISubmitAnswerRepository extends JpaRepository<Question, Long> {
    @Query("SELECT new com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizQuestionQuery(q.id, q.imgURI,q.content, q.score) " +
            "FROM Question q WHERE q.quiz.id = :quizId")
    List<QuizQuestionQuery> findByQuizId(long quizId);

    @Query("SELECT new com.group10.contestPlatform.dtos.quiz.usersubmitanswer.QuizAnswerQuery(q.content, q.correct) " +
            "FROM Answer q WHERE q.id = :id")
    QuizAnswerQuery findById(long id);
}
