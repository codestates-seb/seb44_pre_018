package com.e1i5.stackOverflow.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
public class QuestionCommentDto {
    private long commentId;
    private String authenticatedMemberName;
    private String content;
    private String ImageName;
    private String ImagePath;
    private boolean choose;
    private int commentLikeCount;
    private int commentDislikeCount;


    private LocalDateTime createdAt;
}
