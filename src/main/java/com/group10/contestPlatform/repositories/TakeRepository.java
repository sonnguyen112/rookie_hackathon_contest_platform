package com.group10.contestPlatform.repositories;

import com.group10.contestPlatform.entities.Answer;
import com.group10.contestPlatform.entities.Take;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TakeRepository extends JpaRepository<Take, Long> {

    List<Take> findByUserId(Long userId);
}
