package com.springboot.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PostMapping("/join")
    public ResponseEntity<String> join() {
        return ResponseEntity.ok().body("회원가입이 되었습니다.");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok().body("token");
    }

}
