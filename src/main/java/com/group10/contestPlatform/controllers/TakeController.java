package com.group10.contestPlatform.controllers;

import com.group10.contestPlatform.dtos.ResponMessage;
import com.group10.contestPlatform.dtos.take.TakeDto;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.services.imlp.TakeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/take")
public class TakeController {
    private final TakeServiceImpl takeService;

    @PostMapping("/create")
    public ResponseEntity<ResponMessage> create(@RequestBody TakeDto dto){
        takeService.create(dto);
        return ResponseEntity.status(201)
                .body(new ResponMessage("Created"));
    }

    @GetMapping("")
    public ResponseEntity<List<TakeDto>> getAll(){
        List<Take> list = takeService.getAll();
        return ResponseEntity.status(200)
                .body(list.stream().map(i ->
                        new TakeDto(i)
                        ).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TakeDto> getOne(@PathVariable Long id){
        Optional<Take> take = takeService.get(id);
        return ResponseEntity.status(200)
                .body(new TakeDto(take.get()));
    }

    @GetMapping("/get_by_quizId/{quizId}")
    public ResponseEntity<TakeDto> getByQuizId(@PathVariable Long quizId){
        Optional<Take> take = takeService.getTakeByQuiz(quizId);
        return ResponseEntity.status(200)
                .body(new TakeDto(take.get()));
    }

}
