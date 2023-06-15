package com.e1i5.stackOverflow.question.service;

import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    //private final CommentRepository commentRepository;
    //private final MemberService memberService;

    public QuestionService(QuestionRepository questionRepository
                           //      CommentRepository commentRepository
                           //                  MemberService memberService
    ) {
        this.questionRepository = questionRepository;
        // this.memberService = memberService;
        // this.commentRepository = commentRepository;

    }

    public Question createQuestion(Question question) { //질문 생성 (회원만 질문작성가능)
        Long memberId = getMemberIdFromToken();
        question.getMember().setMemberId(memberId);

        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) { //질문 수정 (질문 수정은 작성자만 가능)
        Question findQuestion = findQuestion(question.getQuestionId());
        Long memberIdFromToken = getMemberIdFromToken();

        // 작성자가 본인이 맞는지 확인하는 코드
        if (findQuestion.getMember().getMemberId() != memberIdFromToken) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_QUESTION);
        }
        Optional.ofNullable(question.getTitle()).ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getContent()).ifPresent(content -> findQuestion.setContent(content));
        findQuestion.setModifiedAt(LocalDateTime.now());

        return questionRepository.save(findQuestion);
    }

    public Question findQuestion(long questionId) { //질문 검색
        return findVerifiedQuestion(questionId);
    }

    public Question findVerifiedQuestion(long questionId) { //특정 질문 검색
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question question = optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return question;
//        /**
//         * response에 List<answer> 필드를 채우기 위한 코드
//         * 위의 updateQuestion과 findQuestion 메서드에도 적용됨
//         */
//        List<Answer> answers = answerService.findAnswers(question.getQuestionId());
//        question.setAnswers(answers); 22조 파트 레퍼

    }

    public Page<Question> findQuestions(int page, int size) { //여러 질문 검색(페이지 단위)
        Page<Question> questionPage = questionRepository.findAll(PageRequest.of(page, size,
                Sort.by("questionId").descending()));

        List<Question> questions = questionPage.getContent();
//        for (Question question : questions) {
//            List<Comment> answers = CommentService.findAnswers(question.getQuestionId());
//            question.setComments(answers);
//        }

        return questionPage;
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findQuestion(questionId);
        Long memberIdFromToken = getMemberIdFromToken();

        if (findQuestion.getMember().getMemberId() != memberIdFromToken) { //작성자가 본인인지 확인하는 코드
            throw new BusinessLogicException(ExceptionCode.CANNOT_DELETE_QUESTION); //아닐경우 예외처리
        }

        questionRepository.deleteById(questionId);
    }

    private Long getMemberIdFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Map)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_LOGIN);
        }

        Map findPrincipal = (Map) principal;
        Long memberId = (Long) findPrincipal.get("memberId");

        return memberId;
    }

}
