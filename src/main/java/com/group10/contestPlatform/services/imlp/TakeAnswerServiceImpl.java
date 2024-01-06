package com.group10.contestPlatform.services.imlp;

import com.group10.contestPlatform.entities.*;
import com.group10.contestPlatform.repositories.AnswerRepository;
import com.group10.contestPlatform.repositories.QuestionRepository;
import com.group10.contestPlatform.repositories.TakeAnswerRepository;
import com.group10.contestPlatform.repositories.TakeRepository;
import com.group10.contestPlatform.services.ITakeAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TakeAnswerServiceImpl implements ITakeAnswerService {
    private final TakeAnswerRepository takeAnswerRepository;
    private final TakeRepository takeRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;


    @Override
    public List<TakeAnswer> getAll() {
        return takeAnswerRepository.findAll();
    }

    @Override
    public TakeAnswer getById(Long id) {
        return takeAnswerRepository.getReferenceById(id);
    }

    @Override
    public List<TakeAnswer> getByTakeId(Long takeId) {
        return takeAnswerRepository.findByTakeId(takeId);
    }
}
