package com.e1i5.stackOverflow.question.mapper;

import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question questionPostDtoToQuestion(QuestionDto.QuestionPostDto requestBody);

    Question questionPatchDtoToQuestion(QuestionDto.QuestionPatchDto requestBody);

    default QuestionResponseDto questionToQuestionResponseDto(Question question){
        QuestionResponseDto responseDto = new QuestionResponseDto();
        responseDto.setQuestionId(question.getQuestionId());
        responseDto.setMemberName(question.getMember().getName());
        responseDto.setTitle(question.getTitle());
        responseDto.setContent(question.getContent());
        responseDto.setView(question.getView());
        responseDto.setCreatedAt(question.getCreatedAt());
        //dto변환 필요
        responseDto.setCommentList(question.getCommentList());
        return responseDto;
    }

    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions) {
        return questions.stream()
                .map(question -> questionToQuestionResponseDto(question))
                .collect(Collectors.toList());
    }
}
