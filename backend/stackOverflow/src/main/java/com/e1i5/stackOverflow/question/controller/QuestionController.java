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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/questions")
@Validated
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper mapper;

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.questionPostDto questionPostDto){
        Question question = mapper.questionPostDtoToQuestion(questionPostDto);
        //question createQuestion = questionService. 서비스 계층
        QuestionResponseDto responseDto = mapper.questionToQuestionResponseDto(createQuestion); //서비스 계층 조금 있다가
        return  new ResponseEntity(responseDto, HttpStatus.CREATED);

    }
}
