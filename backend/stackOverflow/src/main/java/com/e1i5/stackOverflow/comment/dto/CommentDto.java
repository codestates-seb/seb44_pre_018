package com.e1i5.stackOverflow.comment.dto;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
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
        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long commentId;
        private long questionId;
        private String content;
        private boolean choose;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        @Positive
        private int likeCount;

        @Positive
        private int dislikeCount;

        private Comment.CommentStatus commentStatus;
    }

}
