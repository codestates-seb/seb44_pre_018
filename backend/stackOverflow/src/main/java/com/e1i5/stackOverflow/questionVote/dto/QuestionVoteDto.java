package com.e1i5.stackOverflow.questionVote.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.LazyToOne;

@AllArgsConstructor
@Getter
public class QuestionVoteDto {
    private Long questionId;
    private Long memberId;
    private String voteStatus;
}
