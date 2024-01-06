package com.group10.contestPlatform.services.imlp;

import com.group10.contestPlatform.entities.Answer;
import com.group10.contestPlatform.entities.Quiz;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.exceptions.AppException;
import com.group10.contestPlatform.repositories.AnswerRepository;
import com.group10.contestPlatform.services.IAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements IAnswerService {
    private final AnswerRepository answerRepository;
    @Override
    public Optional<Answer> get(Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            Optional<Answer> answer = answerRepository.findById(id);
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }

    @Override
    public List<Answer> getAll() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            List<Answer> list = answerRepository.findAll();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(400, 100);
        }
    }
}
