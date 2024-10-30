package org.zerock.club.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {
    /* 로그인 하지 않은 사용자도 접근할 수 있는 페이지 */
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll......");
    }

    /* 로그인한 사용자만 접근할 수 있는 페이지 */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMember.......");

        log.info("--------------------------------");
        log.info(clubAuthMember);
    }

    /* 관리자 권한이 있는 사용자만 접근할 수 있는 페이지 */
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin......");
    }

    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.com\"")   // 특정 사용자만 허용
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMemberOnly......");
        log.info(clubAuthMember);

        return "/sample/admin";
    }
}
