package com.e1i5.stackOverflow.question.dto;

import com.e1i5.stackOverflow.comment.dto.CommentDto;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;



@Getter
@Setter
public class QuestionResponseDto {

    private long questionId;
    private String memberName;
    private String title;
    private String content;
    private int view;
    private LocalDateTime createdAt;
    private List<QuestionCommentDto> commentList;

}

