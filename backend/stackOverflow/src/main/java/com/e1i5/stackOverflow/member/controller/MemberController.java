package com.e1i5.stackOverflow.member.controller;

import com.e1i5.stackOverflow.member.dto.MemberDto;
import com.e1i5.stackOverflow.member.entity.Member;
import com.e1i5.stackOverflow.member.mapper.MemberMapper;
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

    public MemberController(MemberMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postMemberImage(@RequestPart MultipartFile memberImage){
        Member member = new Member(memberImage.getOriginalFilename());

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody){
        Member member = mapper.memberPostDtoToMember(requestBody);
        return new ResponseEntity<>(mapper.memberToResponseDto(member), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity patchMember(@Valid @RequestBody MemberDto.Patch requestBody){
        return new ResponseEntity<>(HttpStatus.OK);
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
