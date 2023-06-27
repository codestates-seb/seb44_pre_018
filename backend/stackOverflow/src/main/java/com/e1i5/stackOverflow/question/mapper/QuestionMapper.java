package com.e1i5.stackOverflow.question.mapper;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.question.dto.QuestionCommentDto;
import com.e1i5.stackOverflow.question.dto.QuestionDto;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.questionVote.entity.QuestionVote;
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

    @Mapping(source = "member.name", target = "authenticatedMemberName")
    @Mapping(source = "member.profileImageName", target = "ImageName")
    @Mapping(source = "member.profileImagePath", target = "ImagePath")
    @Mapping(source = "likeCount", target = "commentLikeCount")
    @Mapping(source = "dislikeCount", target = "commentDislikeCount")
    QuestionCommentDto commentToQuestionCommentDto(Comment comment);

    default QuestionResponseDto.Question questionToQuestionResponseDto(Question question) {
        if (question == null) {
            return null;
        }

        QuestionResponseDto.Question dto = new QuestionResponseDto.Question();

        dto.setMemberName(question.getMember().getName());

        dto.setCommentList(question.getCommentList().stream()
                .map(comment -> commentToQuestionCommentDto(comment))
                .collect(Collectors.toList()));

        if (question.getQuestionId() != null) {
            dto.setQuestionId(question.getQuestionId());
        }
        dto.setTitle(question.getTitle());
        dto.setContent(question.getContent());
        dto.setView(question.getView());
        dto.setCreatedAt(question.getCreatedAt());
        dto.setLikeCount(Math.toIntExact(question.getQuestionVoteList().stream()
                .filter(questionVote -> questionVote.getVoteType() == QuestionVote.VoteType.LIKE)
                .count()));
        dto.setDisLikeCount(Math.toIntExact(question.getQuestionVoteList().stream()
                .filter(questionVote -> questionVote.getVoteType() == QuestionVote.VoteType.DISLIKE)
                .count()));

        return dto;
    }

    default QuestionResponseDto.QuestionList questionToQuestionListResponseDto(Question question){
        if (question == null) {
            return null;
        }

        QuestionResponseDto.QuestionList dto = new QuestionResponseDto.QuestionList();

        dto.setMemberName(question.getMember().getName());

        dto.setCommentListCount(Math.toIntExact(question.getCommentList().stream().count()));

        if (question.getQuestionId() != null) {
            dto.setQuestionId(question.getQuestionId());
        }
        dto.setTitle(question.getTitle());
        dto.setContent(question.getContent());
        dto.setView(question.getView());
        dto.setCreatedAt(question.getCreatedAt());
        dto.setLikeCount(Math.toIntExact(question.getQuestionVoteList().stream()
                .filter(questionVote -> questionVote.getVoteType() == QuestionVote.VoteType.LIKE)
                .count()));
        dto.setDisLikeCount(Math.toIntExact(question.getQuestionVoteList().stream()
                .filter(questionVote -> questionVote.getVoteType() == QuestionVote.VoteType.DISLIKE)
                .count()));

        return dto;
    }

    default List<QuestionResponseDto.QuestionList> questionsToQuestionResponseDtos(List<Question> questions) {
        return questions.stream()
                .map(question -> questionToQuestionListResponseDto(question))
                .collect(Collectors.toList());
    }
}
