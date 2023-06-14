package com.e1i5.stackOverflow.comment.service;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.comment.repository.CommentRepository;
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
//     private final MemberService memberService;
    // private final QuestionService questionService;

    public CommentService(CommentRepository commentRepository
//                          QuestionService questionService
//                          MemberService memberService
                          ){
//       this.questionService = questionService;
//        this.memberService = memberService;
        this.commentRepository = commentRepository;
    }

    // 댓글 조회 - 비회원, 회원 모두 조회 가능. 특정 질문의 댓글들을 리스트 형태로 확인한다.
    public List<Comment> findCommentList(long questionId){
        List<Comment> commentPage = commentRepository.findAllByQuestionId(questionId);
        return commentPage;
    }


    // 특정 질문의 댓글 조회
    public List<Comment> findComment(long questionId){
        commentRepository.findAllByQuestionId(questionId);

        return null;
    }


    // 댓글 수정 - 해당 댓글 작성자만 수정 가능
    public Comment updateComment(Comment comment){
        return null;
    }

    // 댓글 생성 - 회원만 생성 가능
    public Comment createComment(Comment comment){
        // 회원인지 파악
        return commentRepository.save(comment);
    }

    // 댓글 삭제 - 해당 댓글 작성자, 질문글 작성자 모두 삭제 가능하다.
    public void deleteComment(long commentId) {
        // 저장된 댓글을 찾는다.
        Comment result = findVerifiedComment(commentId);

        // 작성자가 생성한 댓글인지 검증

        commentRepository.delete(result);

    }


    // 해당 댓글을 작성한 사람인지 판단하는 메서드. memberService 이용
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

    }

    // 댓글 dislike count - 로그인 된 회원만 가능
    public void dislikeCount(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        comment.setDislikeCount(comment.getDislikeCount() + 1);
    }
}
