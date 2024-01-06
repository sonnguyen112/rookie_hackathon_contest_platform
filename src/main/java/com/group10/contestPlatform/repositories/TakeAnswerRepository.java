package com.group10.contestPlatform.repositories;

import com.group10.contestPlatform.entities.TakeAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TakeAnswerRepository extends JpaRepository<TakeAnswer, Long> {
    List<TakeAnswer> findByTakeId(Long take_id);
}
