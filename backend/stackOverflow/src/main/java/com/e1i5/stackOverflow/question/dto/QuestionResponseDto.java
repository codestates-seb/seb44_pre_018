package com.e1i5.stackOverflow.question.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;



public class QuestionResponseDto {
@Getter
@Setter
public static class Answers {
    private long questionId;
    private long memberId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int viewCount;
    private int answerCount;
}
    @Getter
    @Setter
    public static class AnswerIds {
        private long questionId;

        private long memberId;

        private String title;

        private String content;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        private List<Long> answerIds;
    }
}
