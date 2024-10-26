package org.zerock.club.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    // 앤트 패턴고 맞는지 검사하는 유틸리티.
    private AntPathMatcher antPathMatcher;
    private String pattern;

    public ApiCheckFilter(String pattern) {
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("REQUESTURI : " + request.getRequestURI());

        log.info(antPathMatcher.match(pattern, request.getRequestURI()));

        if (antPathMatcher.match(pattern, request.getRequestURI())) {
            log.info("ApiCheckFiletr.........................");
            log.info("ApiCheckFiletr.........................");
            log.info("ApiCheckFiletr.........................");
            return;
        }

        filterChain.doFilter(request, response);    // 다음 필터의 단게로 넘어가는 역할
    }
}
