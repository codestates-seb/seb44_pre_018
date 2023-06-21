package com.e1i5.stackOverflow.comment.controller;

import com.e1i5.stackOverflow.comment.dto.CommentDto;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.comment.mapper.CommentMapper;
import com.e1i5.stackOverflow.comment.service.CommentService;
import com.e1i5.stackOverflow.dto.SingleResponseDto;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.repository.MemberRepository;
import com.e1i5.stackOverflow.member.service.MemberService;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.service.QuestionService;
import com.e1i5.stackOverflow.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("v1/comment")
@Validated
@Slf4j
public class CommentController {
    private final static String COMMENT_DEFAULT_URL = "v1/comment";
    private CommentService commentService;
    private QuestionService questionService;
    private CommentMapper mapper;
    private MemberService memberService;

    public CommentController(CommentService commentService,
                             QuestionService questionService,
                             CommentMapper mapper,
                             MemberService memberService) {
        this.commentService = commentService;
        this.questionService = questionService;
        this.mapper = mapper;
        this.memberService = memberService;
    }

    // 댓글 조회 - 비회원도 조회 가능, 질문의 id를 전달받는다.
    // 무한 스크롤 적용. 마지막 댓글 id 전달받는다.
    // 페이지 사이즈는 클라이언트에서 전달
    // 실제 객체를 가져와야한다.
    @GetMapping("/{question-id}")
    public ResponseEntity<List<Comment>> getCommentList(@PathVariable("question-id") @Positive long questionId,
                                                        @RequestParam("lastCommentId") long lastCommentId,
                                                        @RequestParam("size") int size){
        List<Comment> comments = commentService.findCommentList(questionId, lastCommentId, size);
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }


    // 댓글 수정 - 해당 댓글 작성자만 수정 가능
    @PatchMapping("/update/{comment-id}/{member-id}")
    public ResponseEntity updateComment(@PathVariable("comment-id") @Positive long commentId,
                                        @PathVariable("member-id") @Positive long memberId,
            @Valid @RequestBody CommentDto.Patch requestBody){
        // 댓글 작성자인지 판단
        commentService.VerifyCommentAuthor(commentId, memberId);

        requestBody.setCommentId(commentId);
        requestBody.setMemberId(memberId);
        Comment comment = commentService.updateComment(mapper.commentPatchDtoToComment(requestBody));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.commentToCommentResponseDto(comment)), HttpStatus.OK);
    }

    // 댓글 생성 - 회원만 생성 가능 / 특정 질문에 대해 답변을 하는 것이라 하위 설정을 한다.
    @PostMapping("/{question-id}/{member-id}")
    public ResponseEntity postComment(@PathVariable("question-id") long questionId,
                                      @PathVariable("member-id") long authenticatedMemberId,
                                      @Valid @RequestBody CommentDto.Post requestBody){
        // 회원인지 판단 - > jwt 토큰을 받던지 해야할듯
//        long authenticatedMemberId = JwtParseInterceptor.getAuthenticatedMemberId();  // 인가된 사용자를 전달받는다.
//        requestBody.addQuestionId(questionId);
//        requestBody.addAuthenticatedMemberId(authenticatedMemberId);

        Comment comment = mapper.commentPostDtoToComment(requestBody);

        Question question = questionService.findQuestion(questionId);
        comment.setQuestion(question);

        Member member = memberService.findMember(authenticatedMemberId);
        comment.setMember(member);

        Comment createComment = commentService.createComment(comment);
        CommentDto.Response response = mapper.commentToCommentResponseDto(createComment);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    // 댓글 삭제 - 질문글 작성자와 답변 작성자 둘 다 삭제가 가능하다.
    @DeleteMapping("/delete/{comment-id}/{member-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive long commentId,
                                        @PathVariable("member-id") @Positive long memberId){
        // 댓글 작성자거나, 질문 작성자인 경우 삭제 가능
        // 댓글 작성자인지 확인
        commentService.VerifyCommentAuthor(commentId, memberId);
        // 질문 작성자인지 확인 > 수정 여부
        commentService.VerifyQuestionAuthor(commentId, memberId);
        commentService.deleteComment(commentId,memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 댓글 추천수 증가> 로그인한 회원만 가능
    @PatchMapping("/like/{comment-id}")
    public ResponseEntity<Void> likeComment(@PathVariable("comment-id") long commentId){
        // 로그인한 회원인지 검사

        commentService.likeCount(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 댓글 비추천 수 증가 > 로그인한 회원만 가능.
    @PatchMapping("/dislike/{comment-id}")
    public ResponseEntity<Void> dislikeComment(@PathVariable("comment-id") long commentId) {
        // 로그인한 회원인지
        commentService.dislikeCount(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //댓글 채택 > 질문자에게만 보인다.
    @PatchMapping("/choose/{comment-id}/{member-id}")
    public ResponseEntity chooseComment(@PathVariable("comment-id") long commentId,
                                        @PathVariable("member-id") long memberId){
        // 질문자인지 검사
        commentService.VerifyQuestionAuthor(commentId, memberId);
        commentService.chooseComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
