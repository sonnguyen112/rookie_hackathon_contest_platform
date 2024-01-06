package com.group10.contestPlatform.controllers;

import com.group10.contestPlatform.dtos.take.TakeAnswerDto;
import com.group10.contestPlatform.entities.Take;
import com.group10.contestPlatform.entities.TakeAnswer;
import com.group10.contestPlatform.services.imlp.TakeAnswerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/v1/take_answer")
public class TakeAnswerController {
    private final TakeAnswerServiceImpl takeAnswerService;


    @GetMapping("")
    public ResponseEntity<List<TakeAnswerDto>> getAll(TakeAnswerDto dto){
        List<TakeAnswer> list = takeAnswerService.getAll();
        return ResponseEntity.status(200).body(
                list.stream().map(item ->
                        new TakeAnswerDto(item)
                        ).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TakeAnswerDto> getById(@PathVariable Long  id){
        TakeAnswer result = takeAnswerService.getById(id);
        return ResponseEntity.status(200).body(new TakeAnswerDto(result));
    }

    @GetMapping("/get_by_take/{takeId}")
    public ResponseEntity<List<TakeAnswerDto>> getByTakeId(@PathVariable Long  takeId){
        List<TakeAnswer> list = takeAnswerService.getByTakeId(takeId);
        return ResponseEntity.status(200).body(
                list.stream().map(item ->
                        new TakeAnswerDto(item)
                ).toList()
        );
    }
}
