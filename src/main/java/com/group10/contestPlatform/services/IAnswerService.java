package com.group10.contestPlatform.services;

import com.group10.contestPlatform.entities.Answer;

import java.util.List;
import java.util.Optional;

public interface IAnswerService {
    Optional<Answer> get(Long id);

    List<Answer> getAll();
}
