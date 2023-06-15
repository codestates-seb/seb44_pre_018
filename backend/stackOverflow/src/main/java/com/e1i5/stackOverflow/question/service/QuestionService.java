package com.e1i5.stackOverflow.question.service;

import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
        verifyExistsTitle(question.getTitle());
        return questionRepository.save(question);


    }

    public Question updateQuestion(Question question) { //질문 수정 (질문 수정은 작성자만 가능)
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());//요청된 질문이 DB에 없으면 에러

        Optional.ofNullable(question.getTitle()) //제목수정
                .ifPresent(questionTitle->findQuestion.setTitle(questionTitle));
        Optional.ofNullable(question.getContent()) //내용수정
                .ifPresent(questionBody->findQuestion.setContent(questionBody));
        Optional.ofNullable(question.getQuestionStatus()) //글 삭제
                .ifPresent(questionStatus->findQuestion.setQuestionStatus(questionStatus));

        Question updatedQuestion = questionRepository.save(findQuestion);

        return updatedQuestion;

    }

    public Question findQuestion(long questionId) { //질문 검색
        Question findQuestion = findVerifiedQuestion(questionId); //요청된 질문이 DB에 없으면 에러

        findQuestion.setView(findQuestion.getView()+1); //view 1증가
        questionRepository.save(findQuestion); // 수정후 DB에 저장

        return findQuestion;
    }
    public Page<Question> findQuestions(int page, int size, String sort) { //여러 질문 검색(페이지 단위)
        Page<Question> questionPage = questionRepository.findAllByQuestionStatus
                (PageRequest.of(page, size,
                Sort.by(sort).descending()),
                Question.QuestionStatus.QUESTION_EXIST);


        List<Question> questions = questionPage.getContent();


        return questionPage;
    }

    public void deleteQuestion(long questionId) {
        Question findQuestion = findQuestion(questionId);
        questionRepository.delete(findQuestion);
    }


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
}


