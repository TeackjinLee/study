package com.springboot.security.configuration;

import com.springboot.security.service.UserService;
import com.springboot.security.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader("Authorization");
        log.info("authorization : {}", authorization);
        // token안보내면 Block
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            log.error("authorization을 잘못 보냈습니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }

//        if (authorization != null) {
//            String token = authorization.split(" ")[1];
//
//            if (JwtUtil.isExpired(token, secretKey)) {
//                log.error("Token이 만료 되었습니다.");
//                filterChain.doFilter(request, response);
//                return;
//            }
//        }

        String token = null;

        String userName = null;
        // Token꺼내기
        if (authorization != null) {
            token = authorization.split(" ")[1];
            System.out.println("token sss");
            System.out.println(token);
            // UserName Token에서 꺼내기
            userName = JwtUtil.getUserName(token, secretKey);
            // Token Expired되었는지 여부
//            if (JwtUtil.isExpired(token, secretKey)) {
//                    log.error("Token이 만료 되었습니다.");
//                    filterChain.doFilter(request, response);
//                return;
//            }
        }




        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));

        // Detail을 넣어주기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}
