package com.e1i5.stackOverflow.question.controller;

import com.e1i5.stackOverflow.member.dto.MultiResponseDto;
import com.e1i5.stackOverflow.dto.SingleResponseDto;
import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.mapper.QuestionMapper;
import com.e1i5.stackOverflow.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequestMapping("/question")
@Validated
@Slf4j


public class QuestionController {


    private final QuestionMapper mapper;
    private final QuestionService questionService;

    public QuestionController(QuestionMapper mapper, QuestionService questionService) {
        this.mapper = mapper;
        this.questionService = questionService;
    }


    @PostMapping("/create") //질문 생성
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.QuestionPostDto questionPostDto) {

        Question question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.questionToQuestionResponseDto(question)), HttpStatus.CREATED);


    }

    @PatchMapping("/{question_id}") //질문 수정
    public ResponseEntity patchQuestion(@PathVariable("question_id") @Positive long questionId,
                                        @Valid @RequestBody QuestionDto.QuestionPatchDto questionPatchDto) {
        questionPatchDto.setQuestionId(questionId);
        Question patchQuestion = questionService.updateQuestion(mapper.questionPatchDtoToQuestion(questionPatchDto));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.questionToQuestionResponseDto(patchQuestion)), HttpStatus.OK);
    }

    @GetMapping //질문들 조회
    public ResponseEntity getQuestions(@Positive @RequestParam("page") int page,
                                       @Positive @RequestParam("size") int size,
                                       @RequestParam("sort") String sort) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size, sort);

        List<Question> questions = pageQuestions.getContent();


        return new ResponseEntity<>(new MultiResponseDto<>(
                mapper.questionsToQuestionResponseDtos(questions),
                pageQuestions), HttpStatus.OK);
    }

    @GetMapping("/{question_id}") //선택 질문확인
    public ResponseEntity getCommentList(@PathVariable("question_id") @Positive long questionId){
        Question findquestion = questionService.findQuestion(questionId);
        return new ResponseEntity<>(mapper.questionToQuestionResponseDto(findquestion), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{question_id}") //질문 삭제
    public ResponseEntity deleteMember(@PathVariable("question_id") @Positive long questionId) {
        questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
