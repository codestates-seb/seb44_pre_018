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
@RequestMapping("/comment")
//@Validated
public class CommentController {
    private final static String COMMENT_DEFAULT_URL = "/comment";
    private CommentService commentService;
    private CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper){
        this.commentService = commentService;
        this.mapper = mapper;
    }


    // 댓글 조회 - 비회원도 조회 가능
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentList(@PathVariable("comment-id") @Positive long questionId){
        List<Comment> comments = commentService.findCommentList(questionId);
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }

    // 특정 질문의 댓글 조회
    @GetMapping("/{question-id}")
    public ResponseEntity getComment(@PathVariable("comment-id") @Positive long )


    // 댓글 수정 - 해당 댓글 작성자만 수정 가능
    @PatchMapping("/{comment-id}")
    public ResponseEntity updateComment(@Valid @RequestBody CommentDto.Patch requestBody){
        // 작성자인지 검증

        // 수정 후 db에 저장

        return null;
    }

    // 댓글 생성 - 회원만 생성 가능
    @PostMapping("/member/comment/write")
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post requestBody){
        Comment comment = mapper.commentPostDtoToComment(requestBody);
        Comment createComment = commentService.createComment(comment);
        URI location = UriCreator.createUri(COMMENT_DEFAULT_URL, createComment.getCommentId());

        return ResponseEntity.created(location).build();
    }

    // 댓글 삭제 - 해당 댓글 작성자만 생성 가능
    @DeleteMapping

    // 해당 댓글을 작성한 사람인지 판단하는 메서드

    // 댓글 추천수
    @PatchMapping("/member/comment/{comment-id}/like")
    public ResponseEntity<Void> likeComment(@PathVariable long commentId){
        commentService.likeCount(commentId);
        return ResponseEntity.ok().build();
    }

    // 댓글 비추천 수
    @PatchMapping("/memeber/comment/{comment-id}/dislike")
    public ResponseEntity<Void> dislikeComment(@PathVariable long commentId) {
        commentService.dislikeCount(commentId);
        return ResponseEntity.ok().build();
    }

}
