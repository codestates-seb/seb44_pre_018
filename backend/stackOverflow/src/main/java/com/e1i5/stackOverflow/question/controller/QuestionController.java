package com.e1i5.stackOverflow.question.controller;

import com.e1i5.stackOverflow.auth.interceptor.JwtInterceptor;
import com.e1i5.stackOverflow.dto.MultiResponseDto;
import com.e1i5.stackOverflow.dto.SingleResponseDto;
import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.mapper.QuestionMapper;
import com.e1i5.stackOverflow.question.service.QuestionService;
import lombok.NonNull;
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
        long memberId = JwtInterceptor.requestMemberId();

        Question question =questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto),memberId);
        QuestionResponseDto response = mapper.questionToQuestionResponseDto(question);
   // 회원인지 판단 --> 시큐리티 토큰(jwt)
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);


    }

    @PatchMapping("/update/{question_id}") //질문 수정
    public ResponseEntity patchQuestion(@PathVariable("question_id") @Positive @NonNull long questionId,
                                        @Valid @RequestBody QuestionDto.QuestionPatchDto questionPatchDto) {
        long memberId = JwtInterceptor.requestMemberId();

        questionService.QuestionByAuthor(questionId, memberId); // 댓글 작성자메서드 호출
        questionPatchDto.setQuestionId(questionId);
        questionPatchDto.setMemberId(memberId);
        Question patchQuestion = questionService.updateQuestion(mapper.questionPatchDtoToQuestion(questionPatchDto));

        return new ResponseEntity<>
                (new SingleResponseDto<>(mapper.questionToQuestionResponseDto(patchQuestion)), HttpStatus.OK);
    }

    @GetMapping ("/question/search")//질문들 조회
    public ResponseEntity getQuestions(@Positive @RequestParam("page") int page,
                                       @Positive @RequestParam("size") int size                                     ) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size);
        List<Question> questions = pageQuestions.getContent();


        return new ResponseEntity<>(new MultiResponseDto<>(
                mapper.questionsToQuestionResponseDtos(questions),
                pageQuestions), HttpStatus.OK);
    }

    @GetMapping("/{question_id}") //선택 질문확인
    public ResponseEntity getCommentList(@PathVariable("question_id") @Positive long questionId){
        Question findquestion = questionService.findQuestion(questionId);

        System.out.println(findquestion.getCommentList().stream().count());

        QuestionResponseDto responseDto = mapper.questionToQuestionResponseDto(findquestion);
        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{question_id}") //질문 삭제
    public ResponseEntity deleteMember(@PathVariable("question_id") @Positive long questionId){
        long memberId = JwtInterceptor.requestMemberId();

        questionService.QuestionByAuthor(questionId, memberId);
        questionService.deleteQuestionWithComments(questionId);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
