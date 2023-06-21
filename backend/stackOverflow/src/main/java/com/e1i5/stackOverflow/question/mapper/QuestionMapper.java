package com.e1i5.stackOverflow.question.mapper;

import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question questionPostDtoToQuestion(QuestionDto.QuestionPostDto requestBody);

    Question questionPatchDtoToQuestion(QuestionDto.QuestionPatchDto requestBody);

    default QuestionResponseDto questionToQuestionResponseDto(Question question){
        if ( question == null ) {
            return null;
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        if ( question.getQuestionId() != null ) {
            questionResponseDto.setQuestionId( question.getQuestionId() );
        }

        questionResponseDto.setMemberName(question.getMember().getName());
        questionResponseDto.setTitle(question.getTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setView(question.getView());
        questionResponseDto.setCreatedAt(question.getCreatedAt());
        //dto변환 필요
        questionResponseDto.setCommentList(question.getCommentList());
        return questionResponseDto;
    }

    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions) {
        return questions.stream()
                .map(question -> questionToQuestionResponseDto(question))
                .collect(Collectors.toList());
    }
}
