package org.zerock.club.security.filter;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

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
        System.out.println("TEST@@@@@@@@@@@@@@@2222@@@@@2");
        if (antPathMatcher.match(pattern, request.getRequestURI())) {
            log.info("ApiCheckFiletr.........................");
            log.info("ApiCheckFiletr.........................");
            log.info("ApiCheckFiletr.........................");

            boolean checkHeader = checkAuthHeader(request);
            log.info("checkAuthHeader ::::::: " + checkHeader);
            if (checkHeader) {
                filterChain.doFilter(request, response);
                return;
            } else {
                System.out.println("TEST@@@@@@@@@@@@@@@@@@@@");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                // json 리턴 및 한글깨짐 수정.
                response.setContentType("text/html;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", "403");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                return;
            }
        }

        filterChain.doFilter(request, response);    // 다음 필터의 단게로 넘어가는 역할
    }

    private boolean checkAuthHeader(HttpServletRequest request) {
        boolean checkResult = false;

        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader)) {
            log.info("Authorization exit : " + authHeader);
            if (authHeader.equals("12345678")) {
               checkResult = true;
            }
        }
        return checkResult;
    }
}
