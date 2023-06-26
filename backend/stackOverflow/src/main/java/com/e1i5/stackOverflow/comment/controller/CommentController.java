package com.e1i5.stackOverflow.comment.controller;

import com.e1i5.stackOverflow.auth.interceptor.JwtInterceptor;
import com.e1i5.stackOverflow.comment.dto.CommentDto;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.comment.mapper.CommentMapper;
import com.e1i5.stackOverflow.comment.service.CommentService;
import com.e1i5.stackOverflow.dto.MultiResponseDto;
import com.e1i5.stackOverflow.dto.SingleResponseDto;
import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.service.MemberService;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.repository.QuestionRepository;
import com.e1i5.stackOverflow.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.*;

@RestController
@RequestMapping("/comment")
@Validated
@Slf4j
public class CommentController {
    private final static String COMMENT_DEFAULT_URL = "/comment";
    private CommentService commentService;
    private QuestionService questionService;
    private CommentMapper mapper;
    private MemberService memberService;
    private QuestionRepository questionRepository;

    public CommentController(CommentService commentService,
                             QuestionService questionService,
                             CommentMapper mapper,
                             MemberService memberService,
                             QuestionRepository questionRepository) {
        this.commentService = commentService;
        this.questionService = questionService;
        this.mapper = mapper;
        this.memberService = memberService;
        this.questionRepository = questionRepository;
    }

    // 댓글 조회 - 비회원도 조회 가능
    @GetMapping("/{question-id}")
    public ResponseEntity getCommentList(@PathVariable("question-id") @Positive long questionId,
                                                        @RequestParam("page") int page,
                                                        @RequestParam("size") int size){
        Question question = questionRepository.findById(questionId) // 전달받은 질문 id와 일치하는 질문을 질문테이블에서 가져옴
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        Page<Comment> comments = commentService.findCommentList(page, size, question);
        List<Comment> commentList = comments.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(
                mapper.commentsToCommentResponseDtos(commentList),
                comments), HttpStatus.OK);

    }


    // 댓글 수정 - 해당 댓글 작성자만 수정 가능
    @PatchMapping("/update/{question-id}/{comment-id}")
    public ResponseEntity updateComment(@PathVariable("question-id") @Positive @NotNull long questionId,
                                        @PathVariable("comment-id") @Positive @NotNull long commentId,
                                        @Valid @RequestBody CommentDto.Patch requestBody){
        long memberId = JwtInterceptor.requestMemberId();

        requestBody.setQuestionId(questionId);
        requestBody.setCommentId(commentId);
        requestBody.setAuthenticatedMemberId(memberId);
        // 댓글 작성자인지 판단
        commentService.VerifyCommentAuthor(commentId, memberId);

        Comment patchComment = commentService.updateComment(mapper.commentPatchDtoToComment(requestBody));
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.commentToCommentResponseDto(patchComment)), HttpStatus.OK);
    }

    // 댓글 생성 - 회원만 생성 가능 / 특정 질문에 대해 답변을 하는 것이라 하위 설정을 한다.
    @PostMapping("/question-answer/{question-id}")
    public ResponseEntity postComment(@PathVariable("question-id") long questionId,
                                      @Valid @RequestBody CommentDto.Post requestBody){

        long authenticatedMemberId = JwtInterceptor.requestMemberId();

        Comment comment = mapper.commentPostDtoToComment(requestBody);
        Question question = questionService.findQuestion(questionId);
        comment.setQuestion(question);
        Member member = memberService.findMember(authenticatedMemberId);
        comment.setMember(member);
        Comment createComment = commentService.createComment(comment, questionId, authenticatedMemberId);
        CommentDto.Response response = mapper.commentToCommentResponseDto(createComment);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    // 댓글 삭제 - 질문글 작성자와 답변 작성자 둘 다 삭제가 가능하다.
    @DeleteMapping("/delete/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive long commentId){
        long memberId = JwtInterceptor.requestMemberId();
        // 댓글 작성자거나, 질문 작성자인 경우 삭제 가능
        // 댓글 작성자인지 확인
        commentService.VerifyCommentAuthor(commentId, memberId);
        // 질문 작성자인지 확인
        commentService.VerifyQuestionAuthor(commentId, memberId);
        commentService.deleteComment(commentId,memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 댓글 추천수 증가> 로그인한 회원만 가능
    @PatchMapping("/like/{comment-id}")
    public ResponseEntity<Void> likeComment(@PathVariable("comment-id") long commentId){
        long memberId = JwtInterceptor.requestMemberId();
        // 로그인한 회원인지
        Member member = memberService.findMember(memberId);
        if(member.getMemberStatus() == Member.MemberStatus.MEMBER_ACTIVE){
            commentService.likeCount(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // 댓글 비추천 수 증가 > 로그인한 회원만 가능.
    @PatchMapping("/dislike/{comment-id}")
    public ResponseEntity<Void> dislikeComment(@PathVariable("comment-id") @Positive long commentId) {
        long memberId = JwtInterceptor.requestMemberId();
        // 로그인한 회원인지
        Member member = memberService.findMember(memberId);
        if(member.getMemberStatus() == Member.MemberStatus.MEMBER_ACTIVE){
            commentService.dislikeCount(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    //댓글 채택 > 질문자에게만 보인다.
    @PatchMapping("/choose/{comment-id}")
    public ResponseEntity chooseComment(@PathVariable("comment-id") @Positive long commentId){
        long memberId = JwtInterceptor.requestMemberId();
        // 질문자인지 검사
        commentService.VerifyQuestionAuthor(commentId, memberId);
        commentService.chooseComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
