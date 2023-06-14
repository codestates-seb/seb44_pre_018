package com.e1i5.stackOverflow.question.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class QuestionResponseDto {
    private long questionId;
    private long memberId;
    private String name;
    private String title;
    private String body;
    private int score;
    private boolean checked;
    private List<String> tags;
    private int answerCount;
    private String createdAt;
    private String modifiedAt;
}
