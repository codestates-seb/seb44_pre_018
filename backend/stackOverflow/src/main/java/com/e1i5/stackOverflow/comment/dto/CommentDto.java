package com.e1i5.stackOverflow.comment.dto;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private long questionId;

        private long authenticatedMemberId;

        @NotBlank(message = "내용을 작성해주세요")
        private String content;

        public void addQuestionId(long questionId){
            this.questionId = questionId;
        }

        public void addAuthenticatedMemberId(long authenticatedMemberId){
            this.authenticatedMemberId = authenticatedMemberId;
        }

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        @Setter
        @NotNull
        private long commentId;

        @Setter
        @NotNull
        private long questionId;

        @Setter
        @NotNull
        private long authenticatedMemberId;

        @NotBlank(message = "내용을 적어주세요.")
        private String content;

        public void setQuestionId(long questionId) {
            this.questionId = questionId;
        }

        public void setCommentId(long commentId){
            this.commentId = commentId;
        }
        public void setAuthenticatedMemberId(long authenticatedMemberId){
            this.authenticatedMemberId = authenticatedMemberId;
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long commentId; // 댓글 id
        private long memberId;
        private long questionId;
        private String questionTitle;
        private String authenticatedMemberName;
        private String content;  // 댓글 내용
        private boolean choose; // 댓글 채택 여부


        @Positive
        private int likeCount;  // 댓글 좋아요 카운트

        @Positive
        private int dislikeCount;  // 댓글 싫어요 카운트

        private Comment.CommentStatus commentStatus;  // 댓글 상태변수
    }

}
