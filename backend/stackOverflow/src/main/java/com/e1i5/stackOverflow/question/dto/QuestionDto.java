package com.e1i5.stackOverflow.question.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


public class QuestionDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class QuestionPostDto {
        @NotBlank(message = "제목을 작성해야 합니다")
        private String title;
        @NotBlank(message = "내용을 작성해야 합니다")
        private String content;
        private long memberId;
    }

    @AllArgsConstructor
    @Setter
    @Getter
    public static class QuestionPatchDto{

        private String content;
        private String title;
        private long memberId;
        private long questionId;

    }

}

