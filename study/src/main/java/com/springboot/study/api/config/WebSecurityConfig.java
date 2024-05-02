package com.springboot.study.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/member/join").permitAll()
                        .requestMatchers("/admin/login").permitAll()
                        .anyRequest().authenticated()
        );


//        // 인가(접근권한) 설정
//        http.authorizeHttpRequests().requestMatchers("/").permitAll();
//        http.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
//        http.authorizeHttpRequests().requestMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER");
//        http.authorizeHttpRequests().requestMatchers("/user/loginSuccess").hasAnyRole("3", "4", "5");
//
//        // 사이트 위변조 방지
//        http.cors()
//                .and()
//                .csrf().disable()
//                .authorizeHttpRequests();
//
//        // 로그인 설정
//        http.formLogin()
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/user/loginSucess")
//                .failureUrl("/user/login?success=100")
//                .usernameParameter("uid")
//                .passwordParameter("pass");
//
//        // 로그아웃 설정
//        http.logout()
//                .invalidateHttpSession(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
//                .logoutSuccessUrl("/user/login?success=200");

        // 사용자 인증 처리 컴포넌트 서비스 등록
//        http.userDetailsService(service);

        return http.build();

    }

    @Bean
    public PasswordEncoder PasswordEncoder() { //PasswordEncoder는 BCryptPasswordEncoder 의 인터페이스임
        return new BCryptPasswordEncoder();
    }
}
