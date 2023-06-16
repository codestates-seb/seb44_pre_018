package com.e1i5.stackOverflow.questionVote.entity;


import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class QuestionVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionVoteId;
    @Enumerated(EnumType.STRING)
    private VoteType voteType;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;


    public enum VoteType {
        LIKE,
        DISLIKE
    }
}