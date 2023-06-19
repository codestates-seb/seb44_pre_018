package com.e1i5.stackOverflow.comment.dto;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotNull
        private long questionId;


        @NotBlank(message = "내용을 작성해주세요")
        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        @Setter
        private long commentId;

        @NotBlank(message = "내용을 적어주세요.")
        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long commentId; // 댓글 id
        private long questionId;
        private String content;  // 댓글 내용
        private boolean choose; // 댓글 채택 여부
//        private LocalDateTime createdAt;  // 댓글 생성일
//        private LocalDateTime modifiedAt;  // 댓글 수정일

        @Positive
        private int likeCount;  // 댓글 좋아요 카운트

        @Positive
        private int dislikeCount;  // 댓글 싫어요 카운트

        private Comment.CommentStatus commentStatus;  // 댓글 상태변수
    }

}