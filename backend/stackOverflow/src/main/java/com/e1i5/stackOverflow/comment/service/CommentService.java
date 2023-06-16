package com.e1i5.stackOverflow.comment.service;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.comment.repository.CommentRepository;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.service.MemberService;
import com.e1i5.stackOverflow.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
     private final CommentRepository commentRepository;
     private final MemberService memberService;
//     private final QuestionService questionService;

    public CommentService(CommentRepository commentRepository,
//                          QuestionService questionService,
                          MemberService memberService){
//       this.questionService = questionService;
        this.memberService = memberService;
        this.commentRepository = commentRepository;
    }

    // 댓글 목록 조회 - 비회원, 회원 모두 조회 가능. 특정 질문의 댓글들을 리스트 형태로 확인한다.
    public List<Comment> findCommentList(long questionId){
        List<Comment> commentPage = commentRepository.findAllByQuestionId(questionId);
        return commentPage;
    }


    // 댓글 수정 - 해당 댓글 작성자만 수정 가능
    public Comment updateComment(Comment comment){
        // 요청된 답이 db에 존재하는지 확인
        Comment findComment = findVerifiedComment(comment.getCommentId());
        // 내용 수정
        Optional.ofNullable(comment.getContent())
                .ifPresent(commentContent -> findComment.setContent(commentContent));
        // 업데이트 날짜 수정
        Optional.ofNullable(comment.getModifiedAt())
                .ifPresent(commentModifiedAt ->findComment.setModifiedAt(commentModifiedAt));
        // 상태정보 업데이트  ORIGIN > MODIFIED
        Optional.ofNullable(comment.getCommentStatus2())
                .ifPresent(commentStatus2 -> findComment.setCommentStatus(commentStatus2.MODIFIED_COMMENT));
        Comment modifiedComment = commentRepository.save(findComment);

        return modifiedComment;
    }

    // 댓글 생성 - 회원만 생성 가능
    public Comment createComment(Comment comment){
        // 회원인지 파악
        return commentRepository.save(comment);
    }

    //댓글 삭제 - 해당 댓글 작성자, 질문글 작성자 모두 삭제 가능하다.
    public void deleteComment(long commentId) {
        // if(댓글 작성자 || 질문작성자) 삭제 else 예외발생

        // 저장된 댓글을 찾는다.
        Comment result = findVerifiedComment(commentId);

        commentRepository.delete(result);

    }

    // commentId에 해당하는 댓글을 찾는 메서드 보류. 더 간단하게 할 수 있을 듯
    @Transactional
    public Comment findVerifiedComment(long commentId){
        //
        Optional<Comment> optionalComment =
                commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }

    // 댓글 like count - 로그인 된 회원만 가능
    public void likeCount(long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);// 좋아요 수 증가 후 변경 내용 저장

    }

    // 댓글 dislike count - 로그인 된 회원만 가능
    public void dislikeCount(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        comment.setDislikeCount(comment.getDislikeCount() + 1);
        commentRepository.save(comment);
    }

    // 채택 메서드
    public void chooseComment(long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        // 댓글> 질문자 채택글로 변경
        // if(질문 작성자라면)
        comment.setChoose(true);
        commentRepository.save(comment);

    }

    //질문 작성자인지 판단하는 메서드

    //댓글 작성자인지 판단하는 메서드


}
