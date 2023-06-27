package com.e1i5.stackOverflow.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMEBR_EXISTS(409, "Member exists"),


//=========== comment 예외 목록
    COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다."),
    NOT_A_MEMBER(403, "회원이 아닙니다."),
    NOT_A_COMMENT_WRITER(403, "댓글 작성자가 아닙니다."),
    CANNOT_CREATE_COMMENT(403, "회원이 아닙니다."),
    CANNOT_DELETE_COMMENT(403, "댓글을 삭제할 수 없습니다."),
    CNANOT_CHOOSE_THIS_COMMENT(403,"댓글을 채택할 수 없습니다."),

//==========================

    IS_NOT_AN_ADMIN(403, "not an admin"),
    MEMBER_EXISTS(409, "Member exists"),
    TAG_EXISTS(409, "Member exists"),
    MEMBER_NOT_LOGIN(403, "Member not login"),
    COFFEE_NOT_FOUND(404, "Coffee not found"),
    COFFEE_CODE_EXISTS(409, "Coffee Code exists"),
    ORDER_NOT_FOUND(404, "Order not found"),
    CANNOT_CHANGE_ORDER(403, "Order can not change"),
    CANNOT_CHANGE_QUESTION(403, "Question can not change"),
    CANNOT_DELETE_QUESTION(403, "Question can not delete"),
    CANNOT_CHANGE_ANSWER(403, "Answer can not change"),
    CANNOT_DELETE_ANSWER(403, "Answer can not delete"),
    CANNOT_CHANGE_COMMENT(403, "Comment can not change"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    QUESTION_NOT_FOUND(404,"Question not found"),
    QUESTION_EXISTS(409,"question exists"),
    QUESTION_MEMBER_NOT_MATCH(1004,"Question memberId not match"),
    SECRET_QUESTION_QUESTIONID_NOT_MATCH(1818,"Qustion memberId Answer memberId not match and not admin"),
    ANSWER_NOT_MATCH(404,"Answer is not match"),
    ANSWER_MEMBER_NOT_MATCH(404,"Answer's member not match"),
    ANSWER_NOT_FOUND(404,"Answer not found"),
    OPTION_NOT_FOUND(404,"Option not found");




    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
