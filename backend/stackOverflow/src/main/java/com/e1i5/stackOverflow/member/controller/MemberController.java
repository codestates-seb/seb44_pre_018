package com.e1i5.stackOverflow.member.controller;

import com.e1i5.stackOverflow.dto.MultiResponseDto;
import com.e1i5.stackOverflow.dto.SingleResponseDto;
import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.mapper.MemberMapper;
import com.e1i5.stackOverflow.member.repository.MemberRepository;
import com.e1i5.stackOverflow.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/member")
@Slf4j
@Validated
public class MemberController {
    private final MemberMapper mapper;
    private final MemberService memberService;

    public MemberController(MemberMapper mapper, MemberService memberService) {
        this.mapper = mapper;
        this.memberService = memberService;
    }

//    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity patchMemberImage(@RequestPart MultipartFile memberImage){
//        Member member = new Member(memberImage.getOriginalFilename());
//
//        return new ResponseEntity<>(member, HttpStatus.OK);
//    }

    @PostMapping("/signup")
    public ResponseEntity signupMember(@Valid @RequestBody MemberDto.SignupPost requestBody){
        Member member = memberService.signupMember(mapper.memberSignupPostDtoToMember(requestBody));
        MemberDto.Response response = mapper.memberToMemberResponseDto(member);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity loginMember(@Valid @RequestBody MemberDto.LoginPost requestBody) {
        Member member = null;
        try {
            member = memberService.loginMember(mapper.memberLoginPostDtoToMember(requestBody));
            // 예외가 발생하지 않은 경우에 대한 처리
            // ...
        } catch (Exception e) {
            // 예외가 발생한 경우에 대한 처리
            e.printStackTrace(); // 예시로 예외를 출력하는 처리 방법
        }

        MemberDto.Response response = mapper.memberToMemberResponseDto(member);


//        jwy 토큰 발행
//        예시)
//        String token = jwtTokenProvider.createToken(response.getEmail(), response.getRoles());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity patchMember(@PathVariable("memberId") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody){
        requestBody.setMemberId(memberId);
        Member findMember = memberService.updateMember(mapper.memberPatchDtoToMember(requestBody));
        MemberDto.Response response = mapper.memberToMemberResponseDto(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size){
        Page<Member> pageMembers = memberService.findMembers(page-1, size);
        List<Member> members = pageMembers.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.membersToMemberResponseDtos(members),
                pageMembers), HttpStatus.OK);
    }

    @GetMapping("/{member_id}")
    public ResponseEntity getMember(@PathVariable("member_id") @Positive long memberId){
        Member findMember = memberService.findMember(memberId);
        MemberDto.Response response = mapper.memberToMemberResponseDto(findMember);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/{member_id}")
    public ResponseEntity deleteMember(@PathVariable("member_id") @Positive long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
