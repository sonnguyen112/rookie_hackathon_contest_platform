package com.group10.contestPlatform.repositories;

import com.group10.contestPlatform.entities.Quiz;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TakeRepository extends JpaRepository<Take, Long> {

    List<Take> findByUserId(Long userId);
    Optional<Take> findByQuiz(Quiz quiz);
    Optional<Take> findByUserAndQuiz(User user, Quiz quiz);
}

