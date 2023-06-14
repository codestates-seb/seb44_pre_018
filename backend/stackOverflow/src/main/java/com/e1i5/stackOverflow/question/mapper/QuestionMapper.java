package com.e1i5.stackOverflow.question.mapper;

import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(source = "memberId", target = "member.memberId")
    Question questionPostDtoToQuestion(QuestionDto.questionPostDto questionPostDto);
    @Mapping(source = "memberId", target = "member.memberId")
    Question questionPatchDtoToQuestion(QuestionDto.questionPatchDto questionPatchDto);
    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "member.name", target = "name")
    QuestionResponseDto questionToQuestionResponseDto(Question question);
    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "member.name", target = "name")
    List<QuestionResponseDto> questionsToResponseDto(List<Question>questions);
}
