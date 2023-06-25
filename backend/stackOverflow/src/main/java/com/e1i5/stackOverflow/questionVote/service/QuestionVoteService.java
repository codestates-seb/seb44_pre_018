package com.e1i5.stackOverflow.questionVote.service;

import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.question.entity.Question;
import com.e1i5.stackOverflow.question.repository.QuestionRepository;
import com.e1i5.stackOverflow.questionVote.entity.QuestionVote;
import com.e1i5.stackOverflow.questionVote.repository.QuestionVoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class QuestionVoteService {
    private final QuestionVoteRepository questionVoteRepository;
    private final QuestionRepository questionRepository;

    public QuestionVoteService(QuestionVoteRepository questionVoteRepository,
                               QuestionRepository questionRepository) {
        this.questionVoteRepository = questionVoteRepository;
        this.questionRepository = questionRepository;
    }

    public void voteQuestion(QuestionVote questionVote) {
        System.out.println("service.voteQuestion 시작-------------------------------------------------------------------");
        Optional<QuestionVote> optionalQuestionVote =
                questionVoteRepository.findByQuestionAndMember(questionVote.getQuestion(), questionVote.getMember());
        if (optionalQuestionVote.isPresent()) {
            QuestionVote findQuestionVote = optionalQuestionVote.get();
            if(findQuestionVote.getVoteType() == questionVote.getVoteType()){
                //이미 한 투표에 같은 표를 누름
                questionVote.getQuestion().getQuestionVoteList().remove(findQuestionVote);
                questionRepository.save(questionVote.getQuestion());
                questionVoteRepository.delete(findQuestionVote);

            }else {
                //같은 회원이 같은 질문에 다른 투표
                findQuestionVote.setVoteType(questionVote.getVoteType());
                questionVoteRepository.save(findQuestionVote);
            }

        } else {
            //이 질문에 대ㅐ해 투표를 처음한 회원
            questionVoteRepository.save(questionVote);
        }
    }

}
