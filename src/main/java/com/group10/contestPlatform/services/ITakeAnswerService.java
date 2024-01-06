package com.group10.contestPlatform.services;

import com.group10.contestPlatform.dtos.take.TakeAnswerDto;
import com.group10.contestPlatform.entities.TakeAnswer;

import java.util.List;

public interface ITakeAnswerService {
    List<TakeAnswer> getAll();
    TakeAnswer getById(Long id);
    List<TakeAnswer> getByTakeId(Long takeId);
}
