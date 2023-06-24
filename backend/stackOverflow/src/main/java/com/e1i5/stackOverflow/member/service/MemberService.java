package com.e1i5.stackOverflow.member.service;

import com.e1i5.stackOverflow.auth.utils.CustomAuthorityUtils;
import com.e1i5.stackOverflow.comment.service.CommentService;
import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.repository.MemberRepository;
import com.e1i5.stackOverflow.question.service.QuestionService;
import com.e1i5.stackOverflow.helper.event.MemberRegistrationApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional

public class MemberService {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    //private final BCryptPasswordEncoder encoder;



    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder,
                         ApplicationEventPublisher publisher,
                         CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this.publisher = publisher;
        //this.encoder = encoder;
    }

    // 회원가입, 로그인 기능을 공유하는 메서드
    public Member signupMember(Member member){
        verifyExistsEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword()); // member에 저장된 원래 비밀번호 암호화
        member.setPassword(encryptedPassword);// 암호화된 비밀번호를 member의 비밀번호 필드에 저장
        // db에 저장되는 비밀번호는 실제 비밀번호의 암호화된 형태로 저장

        // 추가: DB에 User Role 저장, 사용자 권한 정보 생성
        List<String> roles = authorityUtils.createRoles(member.getEmail());// 사용자 이메일 기반으로 사용자 역할을 생성, 저장
        member.setRoles(roles);// member 객체의 역할 필드에 역할 리스트 설정

        Member savedMember = memberRepository.save(member);
        publisher.publishEvent(new MemberRegistrationApplicationEvent(savedMember));
        return savedMember;
    }

    public Member loginMember(Member member) throws Exception{
        Member findMember = findVerifiedMemberByEmail(member.getEmail());
        String findMemberPassword = findMember.getPassword();

        // 임시 비밀번호 확인
//        if (!encoder.matches(member.getPassword(), findMemberPassword)) {
//            throw new Exception("Invalid password"); // 예외를 던짐
//        }else {
//            System.out.println("비밀번호 일치");
//        }


        return memberRepository.save(findMember);
    }



    public void imageUpload(long memberId, MultipartFile multipartFile){
        //image upload
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();

        try {
            // 경로가 없을시 경로 추가
            File directory = new File(projectPath);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    System.out.println("디렉토리가 생성되었습니다.");
                } else {
                    System.out.println("디렉토리를 생성할 수 없습니다.");
                    throw new IllegalArgumentException("경로가 존재하지 않습니다.");
                }
            }
            if(multipartFile.getContentType().startsWith("image/")){
                File savefile = new File(projectPath, fileName);
                multipartFile.transferTo(savefile);
                // 파일 업로드 성공
            }else {
                throw new IllegalArgumentException("이미지파일만 프로필로 설정할 수 있습니다.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            // 파일 업로드 실패 처리 로직
            throw new IllegalArgumentException("이미지 업로드에 실패했습니다.");
        }

        Member findMember = findVerifiedMemberById(memberId);
        findMember.setProfileImagePath(projectPath);
        findMember.setProfileImageName(fileName);

        memberRepository.save(findMember);
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

    @Transactional(readOnly = true)
    public Member findMember(long memberId)
    {
        return findVerifiedMemberById(memberId);}

    public void deleteMember(long memberId){
        Member findMember = findVerifiedMemberById(memberId);

        memberRepository.delete(findMember);
    }

    private void verifyExistsEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    @Transactional(readOnly = true)
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
