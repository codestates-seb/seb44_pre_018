package com.e1i5.stackOverflow.questionVote.entity;


import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public enum VoteType {
        LIKE,
        DISLIKE;
    }

    public QuestionVote(VoteType voteType, Question question, Member member) {
        this.voteType = voteType;
        this.question = question;
        this.member = member;
    }
}