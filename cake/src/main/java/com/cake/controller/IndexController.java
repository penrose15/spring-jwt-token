package com.cake.controller;

import com.cake.model.Member;
import com.cake.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String root() {
        return "I am groot";
    }

    @PostMapping("/join")
    public String join(@RequestBody Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberService.createMember(member);

        return "회원 가입 완료";
    }

    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }
}
