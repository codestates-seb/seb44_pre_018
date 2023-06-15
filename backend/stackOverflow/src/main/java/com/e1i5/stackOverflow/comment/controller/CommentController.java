package com.e1i5.stackOverflow.comment.controller;

import com.e1i5.stackOverflow.comment.dto.CommentDto;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.comment.mapper.CommentMapper;
import com.e1i5.stackOverflow.comment.service.CommentService;
import com.e1i5.stackOverflow.utils.UriCreator;
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
@RequestMapping("/v1/comment")
@Validated
public class CommentController {
    private final static String COMMENT_DEFAULT_URL = "v1/comment";
    private CommentService commentService;
    private CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper){
        this.commentService = commentService;
        this.mapper = mapper;
    }


    // 댓글 조회 - 비회원도 조회 가능, 질문의 id를 전달받는다.
    @GetMapping("/{question-id}")
    public ResponseEntity<List<Comment>> getCommentList(@PathVariable("question-id") @Positive long questionId){
        List<Comment> comments = commentService.findCommentList(questionId);
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }


    // 댓글 수정 - 해당 댓글 작성자만 수정 가능
    @PatchMapping("/{comment-id}/rewrite")
    public ResponseEntity updateComment(@Valid @RequestBody CommentDto.Patch requestBody){
        // 작성자인지 검증

        // 수정 후 db에 저장

        return null;
    }

    // 댓글 생성 - 회원만 생성 가능
    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post requestBody){
        // 회원인지 판단

        Comment comment = mapper.commentPostDtoToComment(requestBody);
        Comment createComment = commentService.createComment(comment);
        return new ResponseEntity<>(createComment, HttpStatus.OK);
    }

    // 댓글 삭제 - 질문글 작성자와 답변 작성자 둘 다 삭제가 가능하다.
    @DeleteMapping("/{comment-id}/delete")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive long commentId){
        // 댓글 작성자거나, 질문 작성자인 경우 삭제 가능
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 댓글 추천수 증가> 로그인한 회원만 가능
    @PatchMapping("/{comment-id}/like")
    public ResponseEntity<Void> likeComment(@PathVariable("comment-id") long commentId){
        commentService.likeCount(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 댓글 비추천 수 증가 > 로그인한 회원만 가능.
    @PatchMapping("/{comment-id}/dislike")
    public ResponseEntity<Void> dislikeComment(@PathVariable("comment-id") long commentId) {
        // 회원이라면 아래 로직 진행, else 예외 진행
        commentService.dislikeCount(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //댓글 채택 > 질문자에게만 보인다.
    @PostMapping("/{comment-id}/choose")
    public ResponseEntity chooseComment(@PathVariable long commentId){
        commentService.chooseComment(commentId);
        return ResponseEntity.ok("댓글이 채택되었습니다.");
    }


}
