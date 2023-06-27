package com.e1i5.stackOverflow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//서버 작동 테스트 페이지
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<>("server is working!!", HttpStatus.OK);
    }
}