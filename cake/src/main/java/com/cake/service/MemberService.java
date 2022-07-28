package com.cake.service;

import com.cake.model.Member;
import com.cake.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        verifyMember(member.getUsername());
        return memberRepository.save(member);
    }


    public Optional<Member> findByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        return member;
    }

    public void verifyMember(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        if(member.isPresent()) throw new RuntimeException("이미 존재하는 아이디입니다.");
    }
}
