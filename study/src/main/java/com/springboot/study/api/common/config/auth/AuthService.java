package com.springboot.study.api.common.config.auth;

import com.springboot.study.api.common.config.jwt.TokenProvider;
import com.springboot.study.api.member.dto.MemberRequestDto;
import com.springboot.study.api.member.dto.MemberResponseDto;
import com.springboot.study.datasource.common.TokenDto;
import com.springboot.study.datasource.member.Member;
import com.springboot.study.datasource.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        System.out.println("authenticationToken");
        System.out.println(authenticationToken);
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("authentication");
        System.out.println(authentication);
        return tokenProvider.generateTokenDto(authentication);
    }

}
