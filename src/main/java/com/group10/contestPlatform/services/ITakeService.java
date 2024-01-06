package com.group10.contestPlatform.services;

import com.group10.contestPlatform.dtos.take.TakeDto;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.entities.User;

import java.util.List;
import java.util.Optional;

public interface ITakeService {
    void create(TakeDto dto);
    void createUtil(TakeDto dto, User curUser);
    List<Take> getAll();
    Optional<Take> get(Long id);

    Optional<Take> getTakeByQuiz(Long quizId);

}
