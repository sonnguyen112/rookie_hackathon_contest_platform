package com.group10.contestPlatform.repositories;

import com.group10.contestPlatform.entities.CheatInfo;
import com.group10.contestPlatform.entities.Quiz;
import com.group10.contestPlatform.entities.Take;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheatRepository extends JpaRepository<CheatInfo, Long> {



}

