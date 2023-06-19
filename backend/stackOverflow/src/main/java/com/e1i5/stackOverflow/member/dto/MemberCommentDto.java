package com.e1i5.stackOverflow.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

public class MemberCommentDto {

    @Getter
    public static class Patch {
        @Positive
        private long commentId;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String commentTitle;
        private String commentContent;
    }
}
