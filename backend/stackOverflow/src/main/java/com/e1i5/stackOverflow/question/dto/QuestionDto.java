package com.e1i5.stackOverflow.question.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;


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
        @NotNull(message = "태그를 선택해주세요")
        private List<TagDto> tags;


    }

    @AllArgsConstructor
    @Setter
    @Getter
    public static class QuestionPatchDto{


        @NotBlank(message = "내용을 작성해야 합니다")
        private String content;
        @NotBlank(message = "제목을 작성해야 합니다")
        private String title;
        private long memberId;
        private long questionId;

        private List<TagDto> tags;


    }

}

