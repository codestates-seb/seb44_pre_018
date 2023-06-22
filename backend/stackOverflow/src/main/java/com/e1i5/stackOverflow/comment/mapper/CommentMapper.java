package com.e1i5.stackOverflow.comment.mapper;

import com.e1i5.stackOverflow.comment.dto.CommentDto;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDtoToComment(CommentDto.Post requestBody);
    Comment commentPatchDtoToComment(CommentDto.Patch requestBody);

    @Mapping(source = "question.title", target = "questionTitle")
    @Mapping(source = "member.name", target = "authenticatedMemberName")
//    CommentDto.Response commentToCommentResponseDto(Comment comment);

    default CommentDto.Response commentToCommentResponseDto(Comment comment){
        if(comment == null){
            return null;
        }
        CommentDto.Response commentResponseDto = new CommentDto.Response(
                comment.getCommentId(),
                comment.getMember().getMemberId(),
                comment.getQuestion().getQuestionId(),
                comment.getQuestion().getTitle(),
                comment.getMember().getName(),
                comment.getContent(),
                comment.isChoose(),
                comment.getLikeCount(),
                comment.getDislikeCount(),
                comment.getCommentStatus()
        );

        commentResponseDto.setCommentId(comment.getCommentId());
        commentResponseDto.setMemberId(comment.getMember().getMemberId());
        commentResponseDto.setQuestionId(comment.getQuestion().getQuestionId());
        commentResponseDto.setQuestionTitle(comment.getQuestion().getTitle());
        commentResponseDto.setAuthenticatedMemberName(comment.getMember().getName());
        commentResponseDto.setContent(comment.getContent());
        commentResponseDto.setChoose(comment.isChoose());
        commentResponseDto.setLikeCount(comment.getLikeCount());
        commentResponseDto.setDislikeCount(comment.getDislikeCount());
        commentResponseDto.setCommentStatus(comment.getCommentStatus());

        return commentResponseDto;

    }

    default List<CommentDto.Response> commentsToCommentResponseDtos(List<Comment> commentList) {
        return commentList.stream()
                .map(comment -> commentToCommentResponseDto(comment))
                .collect(Collectors.toList());
    }


}
