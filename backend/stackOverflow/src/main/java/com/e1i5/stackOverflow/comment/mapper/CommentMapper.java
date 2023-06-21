package com.e1i5.stackOverflow.comment.mapper;

import com.e1i5.stackOverflow.comment.dto.CommentDto;
import com.e1i5.stackOverflow.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDtoToComment(CommentDto.Post requestBody);
    Comment commentPatchDtoToComment(CommentDto.Patch requestBody);
    CommentDto.Response commentToCommentResponseDto(Comment comment);

    // 댓글 리스트 반환 > 무한스크롤 관련
    List<CommentDto.Response> commentListToCommentResponseDto(List<Comment> commentList);
}
