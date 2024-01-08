package com.group10.contestPlatform.repositories;

import com.group10.contestPlatform.entities.Quiz;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TakeRepository extends JpaRepository<Take, Long> {

    List<Take> findByUserId(Long userId);

    Optional<Take> findByUserAndQuiz(User user, Quiz quiz);
    @Query("select t from Take t where t.quiz.id = :quizId and t.user.id = :userId")
    Optional<Take> findTakesByQuizAndUser(@Param("quizId") Long quizId, @Param("userId")Long userId);
}

