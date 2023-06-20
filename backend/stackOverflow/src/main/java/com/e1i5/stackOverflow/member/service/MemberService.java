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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

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
