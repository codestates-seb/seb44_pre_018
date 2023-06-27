package com.e1i5.stackOverflow.question.mapper;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.question.dto.*;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.entity.Tag;
import com.e1i5.stackOverflow.question.service.QuestionService;
import com.e1i5.stackOverflow.questionVote.entity.QuestionVote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    default Question questionPostDtoToQuestion(QuestionDto.QuestionPostDto requestBody) {

        Question question = new Question();

        question.setTitle(requestBody.getTitle());
        question.setContent(requestBody.getContent());

        List<Tag> tags = tagsDtosToTags(requestBody.getTags(), question);
        //member 보류
        question.setTagNews(tags);

        return question;
    }
    default Question questionPatchDtoToQuestion(
            QuestionService questionService, QuestionDto.QuestionPatchDto requestBody) {



        Question question = new Question();
        question.setQuestionId(requestBody.getQuestionId());
        question.setTitle(requestBody.getTitle());
        question.setContent(requestBody.getContent());


        if(requestBody.getTags() == null){ // 태그 수정을 하지 않는 경우 -> 기존 질문에서 태그를 불러옴
            question.setTagNews(questionService.findVerifiedQuestion(question.getQuestionId()).getTagNews());
        } else { // 태그 수정을 하는 경우
            List<Tag> tagNews = tagsDtosToTags(requestBody.getTags(), question);
            question.setTagNews(tagNews);
        }
//        question.setQuestionStatus(questionPatchDto.getQuestionStatus());

        return question;
    }

    default List<Tag> tagsDtosToTags(List<TagDto> tagsDtos, Question question){

        return tagsDtos.stream().distinct().map(tagDtoNew -> {
            Tag tagNew = new Tag();

            tagNew.addQuestion(question);
            tagNew.setTagName(tagDtoNew.getTagName());
            return tagNew;
        }).collect(Collectors.toList());
    }



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
        dto.setQuestionTags(tagsToTagResponseDtos(
                question.getTagNews()
        ).stream().distinct().collect(Collectors.toList()));
        dto.setLikeCount(Math.toIntExact(question.getQuestionVoteList().stream()
                .filter(questionVote -> questionVote.getVoteType() == QuestionVote.VoteType.LIKE)
                .count()));
        dto.setDisLikeCount(Math.toIntExact(question.getQuestionVoteList().stream()
                .filter(questionVote -> questionVote.getVoteType() == QuestionVote.VoteType.DISLIKE)
                .count()));

        return dto;
    }
    default List<TagResponseDto> tagsToTagResponseDtos(List<Tag> tagNews) {

        return tagNews.stream()
                .map(tagNew -> TagResponseDto
                        .builder()
                        .tagName(tagNew.getTagName())
                        .build())
                .distinct()
                .collect(Collectors.toList());
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
