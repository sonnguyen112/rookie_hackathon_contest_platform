package com.group10.contestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group10.contestPlatform.entities.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
}
