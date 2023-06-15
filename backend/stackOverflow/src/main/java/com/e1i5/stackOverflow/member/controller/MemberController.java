package com.e1i5.stackOverflow.member.controller;

import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.mapper.MemberMapper;
import com.e1i5.stackOverflow.member.repository.MemberRepository;
import com.e1i5.stackOverflow.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody){
        Member member = memberService.createMember(mapper.memberPostDtoToMember(requestBody));

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(member), HttpStatus.OK);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity patchMember(@PathVariable("memberId") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody,
                                      @RequestPart MultipartFile memberImage){
        Member findMember = memberService.updateMember(mapper.memberPatchDtoToMember(requestBody), memberImage);

        return new ResponseEntity<>(findMember, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{member_id}")
    public ResponseEntity getMember(@PathVariable("member_id") @Positive long memberId){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{member_id}")
    public ResponseEntity deleteMember(@PathVariable("member_id") @Positive long memberId){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
