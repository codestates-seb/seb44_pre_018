package com.e1i5.stackOverflow.member.entity;

import com.e1i5.stackOverflow.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class MemberQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberQuestionId;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
