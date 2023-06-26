package com.e1i5.stackOverflow.question.service;

import com.e1i5.stackOverflow.comment.entity.Comment;
import com.e1i5.stackOverflow.comment.repository.CommentRepository;
import com.e1i5.stackOverflow.comment.service.CommentService;
import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.service.MemberService;
import com.e1i5.stackOverflow.question.dto.QuestionResponseDto;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.repository.QuestionRepository;
import com.e1i5.stackOverflow.questionVote.entity.QuestionVote;
import com.e1i5.stackOverflow.questionVote.service.QuestionVoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    private final MemberService memberService;

    private final CommentRepository commentRepository;

    private final QuestionVoteService questionVoteService;

    public QuestionService(QuestionRepository questionRepository,
                           MemberService memberService,
                           CommentRepository commentRepository,
                           QuestionVoteService questionVoteService) {
        this.questionRepository = questionRepository;
        this.memberService = memberService;
        this.commentRepository = commentRepository;
        this.questionVoteService = questionVoteService;
    }

    public Question createQuestion(Question question, long memberId ) { //질문 생성 (회원만 질문작성가능)
        verifyExistsTitle(question.getTitle());
        Member member = memberService.findVerifiedMemberById(memberId);
        question.setMember(member);
        //memberService.findVerifiedMemberById(question.getMember().getMemberId()); //회원인지 확인

        return questionRepository.save(question);

    }




    public Question updateQuestion(Question question) { //질문 수정 (질문 수정은 작성자만 가능)
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());//요청된 질문이 DB에 없으면 에러

        Optional.ofNullable(question.getTitle()) //제목수정
                .ifPresent(questionTitle -> findQuestion.setTitle(questionTitle));
        Optional.ofNullable(question.getContent()) //내용수정
                .ifPresent(questionContent -> findQuestion.setContent(questionContent));
        Optional.ofNullable(question.getQuestionStatus()) //글 삭제
                .ifPresent(questionStatus -> findQuestion.setQuestionStatus(questionStatus));

        Question updatedQuestion = questionRepository.save(findQuestion);

        return updatedQuestion;

    }


    public Question findQuestion(long questionId) { //질문 검색
        Question findQuestion = findVerifiedQuestion(questionId); //요청된 질문이 DB에 없으면 에러

        findQuestion.setView(findQuestion.getView() + 1); //view 1증가
        questionRepository.save(findQuestion); // 수정후 DB에 저장

        return findQuestion;
    }

    public Page<Question> findQuestions(int page, int size, String sortBy, String keyword) { // 여러 질문 검색

        Sort sort;

        switch (sortBy) {
            case "view":
                sort = Sort.by(Sort.Direction.DESC, "view");
                break;

//            case "created_at":
//                System.out.println("--생성일 순--");
//                sort = Sort.by(Sort.Direction.DESC, "created_at");
//                break;

            default:
                sort = Sort.by(Sort.Direction.DESC, "questionId");
                break;
        }

        // 특정 단어가 title에 포함된 모든 Question을 조회합니다.
        List<Question> questions = questionRepository.findAll(sort);


        // 특정 단어와 연관된 Question만 필터링하여 반환합니다.
        List<Question> relatedQuestions;
        if (keyword != null && !keyword.isEmpty()) {
            relatedQuestions = questions.stream()
                    .filter(question -> question.getTitle().contains(keyword))
                    .collect(Collectors.toList());
        } else {
            relatedQuestions = questions;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Question> resultPage = new PageImpl<>(relatedQuestions, pageable, relatedQuestions.size());

        return resultPage;
    }

//    public void deleteQuestion(long questionId) {
//        Question findQuestion = findQuestion(questionId);
//        questionRepository.delete(findQuestion);
//    }


    private void verifyExistsTitle(String title) {//이미 글이 존재하면 에러
        Optional<Question> question = questionRepository.findByTitle(title);
        if (question.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_EXISTS);
        }
    }

    public Question findVerifiedQuestion(long questionId) { //요청된 질문이 DB에 없으면 에러
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;



    }


    //질문글 수정,삭제시 질문 작성자만 가능하게하도록하는 메서드
    public void QuestionByAuthor(long questionId, long memberId) {

        Question question = findVerifiedQuestion(questionId);

        if (question.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_MEMBER_NOT_MATCH);
        }
    }

    // 질문글 삭제시 답변도 같이 삭제하는 메서드
    public void deleteQuestion(long questionId) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        // 질문 삭제
        questionRepository.delete(question);
    }

    public Page<Question> getRelatedQuestions(int page, int size, String keyword){
        // 특정 단어가 title에 포함된 모든 Question을 조회합니다.
        List<Question> questions = questionRepository.findAll();

        // 특정 단어와 연관된 Question만 필터링하여 반환합니다.
        List<Question> relatedQuestions = questions.stream()
                .filter(question -> question.getTitle().contains(keyword))
                .collect(Collectors.toList());

        // 페이지네이션을 적용하여 결과를 반환합니다.
        return new PageImpl<>(relatedQuestions,
                PageRequest.of(page, size, Sort.by("questionId").descending()), relatedQuestions.size());
    }

    public Question voteQuestion(long memberId, long questionId, String voteStatus){
        Question question = findQuestion(questionId);
        Member member = memberService.findMember(memberId);



        QuestionVote questionVote = new QuestionVote(QuestionVote.VoteType.valueOf(voteStatus), question, member);
        questionVoteService.voteQuestion(questionVote);
        return question;
    }
}







