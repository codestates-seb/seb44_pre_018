package com.e1i5.stackOverflow.member.service;

import com.e1i5.stackOverflow.comment.service.CommentService;
import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.repository.MemberRepository;
import com.e1i5.stackOverflow.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member signupMember(Member member){
        verifyExistsEmail(member.getEmail());
        //Member saveMember = memberRepository.save(member);

        //회원가입 이메일 추가부분
        return memberRepository.save(member);
    }

    public Member loginMember(Member member) throws Exception{
        Member findMember = findVerifiedMemberByEmail(member.getEmail());

        // 임시 비밀번호 확인
        if (!findMember.getPassword().equals(member.getPassword())) {
            throw new Exception("Invalid password"); // 예외를 던짐
        }


        return memberRepository.save(findMember);
    }

    public Member updateMember(Member member){
        Member findMember = findVerifiedMemberById(member.getMemberId());

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));

        return memberRepository.save(findMember);
    }

    public Page<Member> findMembers(int page, int size){
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public Member findMember(long memberId){return findVerifiedMemberById(memberId);}

    public void deleteMember(long memberId){
        Member findMember = findVerifiedMemberById(memberId);

        memberRepository.delete(findMember);
    }

    private void verifyExistsEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    public Member findVerifiedMemberById(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private Member findVerifiedMemberByEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }
}
