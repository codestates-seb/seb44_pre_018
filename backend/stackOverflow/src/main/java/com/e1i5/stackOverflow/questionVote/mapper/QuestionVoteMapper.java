package com.e1i5.stackOverflow.questionVote.mapper;

import com.e1i5.stackOverflow.questionVote.dto.QuestionVoteDto;
import com.e1i5.stackOverflow.questionVote.entity.QuestionVote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionVoteMapper {
    QuestionVote QuestionVoteDtoToQuestionVote(QuestionVoteDto questionVoteDto);
}
