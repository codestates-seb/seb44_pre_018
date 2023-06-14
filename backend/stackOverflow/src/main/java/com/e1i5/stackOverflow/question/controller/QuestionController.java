package com.e1i5.stackOverflow.question.controller;

import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.mapper.QuestionMapper;
import com.e1i5.stackOverflow.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@RestController
@RequestMapping("/questions")
@Validated
@RequiredArgsConstructor
public class QuestionController {


    private final QuestionMapper mapper;

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.QuestionPostDto questionPostDto){
        Question question = mapper.questionPostDtoToQuestion(questionPostDto);
        return  new ResponseEntity<>(mapper.questionsToResponseDto(question), HttpStatus.OK);

    }
    @PatchMapping
    public ResponseEntity patchQuestion(@Valid @RequestBody QuestionDto.QuestionPatchDto questionPatchDto){
        return new ResponseEntity<>(HttpStatus.OK);


    }
    @GetMapping
    public ResponseEntity getQuestions(){
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{question_id}")
    public ResponseEntity getQuestion(@PathVariable("question_id")@Positive long questionId){
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{question_id}")
    public ResponseEntity deleteMember(@PathVariable("question_id") @Positive long questionId){
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
