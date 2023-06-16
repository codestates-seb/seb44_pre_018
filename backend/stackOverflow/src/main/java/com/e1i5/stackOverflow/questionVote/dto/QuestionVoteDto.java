package com.e1i5.stackOverflow.questionVote.dto;


import com.e1i5.stackOverflow.questionVote.entity.QuestionVote;
import lombok.*;

public class QuestionVoteDto {
    @NoArgsConstructor
    @Setter
    @Getter
    public static class QuestionVotePostDto {
        private QuestionVote.VoteType voteType;
        private long questionId;
        private long memberId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class QuestionVotePatchDto {
        private long questionVoteId;
        private QuestionVote.VoteType voteType;
        private long memberId;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class QuestionVoteResponseDto {
        private long questionVoteId;
        private long memberId;
        private long questionId;
        private QuestionVote.VoteType voteType;
    }
}