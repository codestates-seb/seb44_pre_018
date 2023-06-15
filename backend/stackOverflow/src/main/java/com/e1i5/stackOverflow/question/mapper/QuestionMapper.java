package com.e1i5.stackOverflow.question.mapper;

import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question questionPostDtoToQuestion(QuestionDto.QuestionPostDto questionPostDto);


    Question questionPatchDtoToQuestion(QuestionDto.QuestionPatchDto questionPatchDto);

    QuestionResponseDto questionToQuestionResponseDto(Question question);

    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions) {
        return questions.stream()
                .map(question -> questionToQuestionResponseDto(question))
                .collect(Collectors.toList());
    }
}
