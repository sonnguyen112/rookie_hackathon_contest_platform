package com.group10.contestPlatform.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group10.contestPlatform.entities.Quiz;
import com.group10.contestPlatform.entities.User;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByHost(User user);
    Optional<Quiz> findBySlug(String slug);
}
