package com.e1i5.stackOverflow.question.dto;

import com.e1i5.stackOverflow.member.dto.MemberDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
public class QuestionResponseDto {

    private long questionId;
    private long memberId;
    private String title;
    private String content;
    private int view;
private MemberDto.Response member;
private LocalDateTime createdAt;

}

