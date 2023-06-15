package com.e1i5.stackOverflow.member.service;

import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final StorageService storageService;

    public MemberService(MemberRepository memberRepository, StorageService storageService) {
        this.memberRepository = memberRepository;
        this.storageService = storageService;
    }

    public Member createMember(Member member){
        verifyExistsEmail(member.getEmail());
        Member saveMember = memberRepository.save(member);

        //회원가입 이메일 추가부분
        return saveMember;
    }

    public Member updateMember(Member member, MultipartFile memberImage){
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(memberImage)
                        .ifPresent(image -> findMember.setProfileImageName(image.getOriginalFilename()));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findMember.setEmail(email));

//        storageService.store(memberImage);
        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId){return findVerifiedMember(memberId);}

    public void deleteMember(long memberId){
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }

    private void verifyExistsEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMEBR_EXISTS);
    }

    private Member findVerifiedMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }
}
