package com.e1i5.stackOverflow.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
public class QuestionCommentDto {
    private String name;
    private String content;
    private String ImageName;
    private String ImagePath;
    private int likeCount;
    private int dislikeCount;

    private LocalDateTime createdAt;
}
