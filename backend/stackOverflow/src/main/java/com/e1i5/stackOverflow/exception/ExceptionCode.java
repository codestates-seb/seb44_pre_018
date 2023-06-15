package com.e1i5.stackOverflow.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMEBR_EXISTS(409, "Member exists"),

    COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다."),
    NOT_A_MEMBER(404, "회원이 아닙니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
