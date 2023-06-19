package com.e1i5.stackOverflow.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

public class MemberQuestionDto {
    @Getter
    public static class Patch {
        @Positive
        private long questionId;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String questionTitle;
        private String questionContent;
    }
}
