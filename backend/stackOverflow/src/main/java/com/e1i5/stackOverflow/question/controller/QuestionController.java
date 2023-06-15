package com.e1i5.stackOverflow.question.controller;

import com.e1i5.stackOverflow.Dto.MultiResponseDto;
import com.e1i5.stackOverflow.Dto.SingleResponseDto;
import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.mapper.QuestionMapper;
import com.e1i5.stackOverflow.question.service.QuestionService;
import com.e1i5.stackOverflow.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/questions")
@Validated

public class QuestionController {

    private final static String QUESTION_DEFAULT_URL = "/api/questions";
    private final QuestionMapper mapper;
    private final QuestionService questionService;

    public QuestionController(QuestionMapper mapper, QuestionService questionService) {
        this.mapper = mapper;
        this.questionService = questionService;
    }


    @PostMapping //질문 생성
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionDto.QuestionPostDto questionPostDto){
<<<<<<< HEAD
        Question question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto));

        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, question.getQuestionId());

        return  new ResponseEntity<>(mapper.questionToQuestionResponseDto(question), HttpStatus.CREATED);
=======
        Question question = mapper.questionPostDtoToQuestion(questionPostDto);
        return  new ResponseEntity<>(question, HttpStatus.OK);
>>>>>>> afa8f86d9c61d5c805df18d7b9b827f01f5164d2

    }
    @PatchMapping("/{question-id}") //질문 수정
    public ResponseEntity patchQuestion(@PathVariable("question-id") @Positive long questionId,
                                            @Valid @RequestBody QuestionDto.QuestionPatchDto questionPatchDto){
        questionPatchDto.setQuestionId(questionId);
        Question patchQuestion =questionService.updateQuestion( mapper.questionPatchDtoToQuestion(questionPatchDto));

        return new ResponseEntity<>((mapper.questionToQuestionResponseDto(patchQuestion)),HttpStatus.OK);
        // return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);


    }
    @GetMapping //질문들 조회
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size);
        List<Question> questions = pageQuestions.getContent();

        List<QuestionResponseDto> responses =mapper.questionsToQuestionResponseDtos(questions); //안된다면 원인 1

        return new ResponseEntity<>(new MultiResponseDto<>(responses, pageQuestions), HttpStatus.OK);
    }
    @GetMapping("/{question_id}") //특정 질문 조회
    public ResponseEntity getQuestion(@PathVariable("question_id")@Positive long questionId){
        Question question = questionService.findQuestion(questionId);

        QuestionResponseDto response = mapper.questionToQuestionResponseDto(question);
        return  new ResponseEntity<>(new SingleResponseDto<>(response),HttpStatus.OK);
    }
    @DeleteMapping("/{question_id}") //질문 삭제
    public ResponseEntity deleteMember(@PathVariable("question_id") @Positive long questionId){
        questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
