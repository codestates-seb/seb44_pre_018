package com.e1i5.stackOverflow.member.entity;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.question.entity.Question;

import javax.persistence.*;

public class MemberComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCommentId;

    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
