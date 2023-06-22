package com.e1i5.stackOverflow.question.mapper;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.question.dto.QuestionCommentDto;
import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question questionPostDtoToQuestion(QuestionDto.QuestionPostDto requestBody);

    Question questionPatchDtoToQuestion(QuestionDto.QuestionPatchDto requestBody);

//    @Mapping(source = "member.name", target = "memberName")
//    @Mapping(source = "question.commentList", target = "commentList")
//    QuestionResponseDto questionToQuestionResponseDto(Question question);

//    default List<QuestionCommentDto> commentListToQuestionResponseDtoList(List<Comment> commentList){
//        return commentList.stream()
//                .map(comment -> new QuestionCommentDto(
//                        comment.getMember().getName(),
//                        comment.getContent(),
//                        comment.getMember().getProfileImageName(),
//                        comment.getMember().getProfileImagePath(),
//                        comment.getLikeCount(),
//                        comment.getDislikeCount(),
//                        comment.getCreatedAt()
//                ))
//                .collect(Collectors.toList());
//    }

    @Mapping(source = "member.name", target = "name")
    @Mapping(source = "member.profileImageName", target = "ImageName")
    @Mapping(source = "member.profileImagePath", target = "ImagePath")
    QuestionCommentDto commentToQuestionCommentDto(Comment comment);

    default QuestionResponseDto questionToQuestionResponseDto(Question question) {
        if (question == null) {
            return null;
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        questionResponseDto.setMemberName(question.getMember().getName());

        questionResponseDto.setCommentList(question.getCommentList().stream()
                .map(comment -> commentToQuestionCommentDto(comment))
                .collect(Collectors.toList()));

        if (question.getQuestionId() != null) {
            questionResponseDto.setQuestionId(question.getQuestionId());
        }
        questionResponseDto.setTitle(question.getTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setView(question.getView());
        questionResponseDto.setCreatedAt(question.getCreatedAt());

        return questionResponseDto;
    }

    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions) {
        return questions.stream()
                .map(question -> questionToQuestionResponseDto(question))
                .collect(Collectors.toList());
    }
}
