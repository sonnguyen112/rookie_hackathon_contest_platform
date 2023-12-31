package com.group10.contestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group10.contestPlatform.entities.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
}
