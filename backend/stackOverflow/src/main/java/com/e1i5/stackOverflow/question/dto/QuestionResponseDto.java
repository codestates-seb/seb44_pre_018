package com.e1i5.stackOverflow.question.dto;

import com.e1i5.stackOverflow.comment.dto.CommentDto;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.member.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;




public class QuestionResponseDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Question {
        private long questionId;
        private String memberName;
        private String title;
        private String content;
        private int likeCount;
        private int disLikeCount;
        private int view;
        private LocalDateTime createdAt;
        private List<QuestionCommentDto> commentList;
        private List<TagResponseDto> questionTags;


    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class QuestionList {
        private long questionId;
        private String memberName;
        private String title;
        private String content;
        private int view;
        private LocalDateTime createdAt;
        private int commentListCount;
        private int likeCount;
        private int disLikeCount;
    }

}

