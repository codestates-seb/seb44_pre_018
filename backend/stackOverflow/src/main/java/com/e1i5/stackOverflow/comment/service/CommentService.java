package com.e1i5.stackOverflow.comment.service;

import com.e1i5.stackOverflow.comment.dto.CommentDto;
import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.comment.mapper.CommentMapper;
import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.comment.repository.CommentRepository;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.service.MemberService;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.repository.QuestionRepository;
import com.e1i5.stackOverflow.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
     private final CommentRepository commentRepository;
     private final QuestionRepository questionRepository;
     private final MemberService memberService;
     private final QuestionService questionService;
     private final CommentMapper mapper;

    public CommentService(CommentRepository commentRepository,
                          QuestionService questionService,
                          MemberService memberService,
                          QuestionRepository questionRepository,
                          CommentMapper mapper){
       this.questionService = questionService;
        this.memberService = memberService;
        this.commentRepository = commentRepository;
        this.questionRepository = questionRepository;
        this.mapper = mapper;
    }

    // 댓글 목록 조회 - 비회원, 회원 모두 조회 가능.
    // 댓글 목록 조회 - 비회원, 회원 모두 조회 가능. 특정 질문의 댓글들을 리스트 형태로 확인한다.
    public Page<Comment> findCommentList(int page, int size){
        // lastCommentId도 같이 전달해 다음 페이지 댓글 목록을 list로 조회한다.
        return commentRepository.findAll(PageRequest.of(page, size,
                Sort.by("commentId").descending()));

    }


    // 댓글 수정 - 해당 댓글 작성자만 수정 가능 엔드포인트에서 멤버id, userid를 전달받음
    public Comment updateComment(Comment comment){
        // 질문에서 등록된 memberId와
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
        memberService.findVerifiedMemberById(comment.getMember().getMemberId());
        // 질문 존재 여부 파악
        return commentRepository.save(comment);
    }

    //댓글 삭제 - 해당 댓글 작성자, 질문글 작성자 모두 삭제 가능.
    public void deleteComment(long commentId, long memberId) {
        // if(댓글 작성자 || 질문작성자) 삭제 else 예외발생
        VerifyQuestionAuthor(commentId, memberId);
        VerifyCommentAuthor(commentId, memberId);
        // 예외가 발생하지 않으면 저장된 댓글을 찾는다.
        Comment result = findVerifiedComment(commentId);
        commentRepository.delete(result);

    }

    // commentId에 해당하는 댓글을 찾는 메서드. 댓글이 존재하는지 확인한다.
    @Transactional
    public Comment findVerifiedComment(long commentId){
        Optional<Comment> optionalComment =
                commentRepository.findById(commentId);
        Comment findComment =
                optionalComment.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }

    // 댓글 수정시 사용 - 39-55
    public Member findCommentMember(long commentId){
        Comment findComment = findVerifiedComment(commentId);
        return findComment.getMember();
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
        comment.setChoose(true);
        comment.setCommentStatus(Comment.CommentStatus.ANSWER_COMMENT); // 댓글> 질문자 채택글로 변경
        commentRepository.save(comment);

    }

    //질문 작성자인지 판단하는 메서드
    public void VerifyQuestionAuthor(long commentId, long memberId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        Question question = comment.getQuestion(); // 찾은 답변의 질문정보를 가져온다.
        if(question == null || question.getMember() == null || question.getMember().getMemberId() != memberId){ // 동일인인지 비교
            throw new BusinessLogicException(ExceptionCode.QUESTION_MEMBER_NOT_MATCH);
        }
    }
    //질문의 역할인지 확인

//    댓글 작성자인지 판단하는 메서드
//    전달 받은 memberId가 comment에 저장된 memberId와 같은지 비교
    public void VerifyCommentAuthor(long commentId, long memberId){
        Comment patchComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        if(patchComment.getMember() == null || patchComment.getMember().getMemberId() != memberId){
            throw new BusinessLogicException(ExceptionCode.NOT_A_COMMENT_WRITER);

        }

    }

}
