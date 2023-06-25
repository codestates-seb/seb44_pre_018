package com.e1i5.stackOverflow.member.controller;

import com.e1i5.stackOverflow.auth.interceptor.JwtInterceptor;
import com.e1i5.stackOverflow.dto.MultiResponseDto;
import com.e1i5.stackOverflow.dto.SingleResponseDto;
import com.e1i5.stackOverflow.exception.BusinessLogicException;
import com.e1i5.stackOverflow.exception.ExceptionCode;
import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.mapper.MemberMapper;
import com.e1i5.stackOverflow.member.service.MemberService;
import com.e1i5.stackOverflow.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("/member")
@Slf4j
@Validated
public class MemberController{
    private final static String MEMBER_DEFAULT_URL = "/member";
    private final MemberMapper mapper;
    private final MemberService memberService;

    //private final JwtTokenProvider jwtTokenProvider;

    public MemberController(MemberMapper mapper, MemberService memberService) {
        this.mapper = mapper;
        this.memberService = memberService;
       // this.jwtTokenProvider = jwtTokenProvider;
    }

//    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity patchMemberImage(@RequestPart MultipartFile memberImage){
//        Member member = new Member(memberImage.getOriginalFilename());
//
//        return new ResponseEntity<>(member, HttpStatus.OK);
//    }
/**
 * signupMember, loginMember는 레퍼런스 상 엔드포인트가 같고 동일한 서비스 계층으로 전달되지만
 * 회원가입의 response body를 json의 형태로 반환하는 방법을 찾지 못해 해당 프로젝트에서는 둘을 구분하였습니다.
 * */
    @PostMapping("/signup")
    public ResponseEntity signupMember(@Valid @RequestBody MemberDto.SignupPost requestBody){
        Member member = memberService.signupMember(mapper.memberSignupPostDtoToMember(requestBody));
//        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId());
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponseDto(member)),
                HttpStatus.OK);
    }

    @PostMapping // securityConfiguration을 통해 경로 변경 >  /auth/login
    public ResponseEntity loginMember(@Valid @RequestBody MemberDto.LoginPost requestBody) {
        Member member = mapper.memberLoginPostDtoToMember(requestBody);
        // 동일한 로직이다.
        Member createdMember = memberService.signupMember(member);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createdMember.getMemberId());
       return ResponseEntity.created(location).build();
    }

    @PatchMapping("/upload")
    public ResponseEntity memberIamgeUpload(@RequestParam("file") MultipartFile multipartFile){
        long memberId = JwtInterceptor.requestMemberId();

        memberService.imageUpload(memberId, multipartFile);
        Member findMember = memberService.findMember(memberId);
        MemberDto.Response response = mapper.memberToMemberResponseDto(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity patchMember(@Valid @RequestBody MemberDto.Patch requestBody){
        long memberId = JwtInterceptor.requestMemberId();

        requestBody.setMemberId(memberId);
        Member findMember = memberService.updateMember(mapper.memberPatchDtoToMember(requestBody));
        MemberDto.Response response = mapper.memberToMemberResponseDto(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size){
        long memberId = JwtInterceptor.requestMemberId();
        // 전달받은 id 가 admin과 같다면
        Member visit = memberService.findVerifiedMemberById(memberId);

        if(visit.getEmail().equals("admin@gmail.com")){
            Page<Member> pageMembers = memberService.findMembers(page-1, size);
            List<Member> members = pageMembers.getContent();

            return new ResponseEntity<>(
                    new MultiResponseDto<>(mapper.membersToMemberResponseDtos(members),
                    pageMembers), HttpStatus.OK);}
        else{
            throw new BusinessLogicException(ExceptionCode.IS_NOT_AN_ADMIN);
        }


    }

    @GetMapping("/getmember")
    public ResponseEntity getMember(){
        long memberId = JwtInterceptor.requestMemberId();

        Member findMember = memberService.findMember(memberId);
        MemberDto.Response response = mapper.memberToMemberResponseDto(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/delete") // 회원 삭제 > 지금 탈퇴기능으로 구현인지. 권한이 user로만 설정됨.
    public ResponseEntity deleteMember(){
        long memberId = JwtInterceptor.requestMemberId();
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
