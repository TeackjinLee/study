package org.zerock.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        log.info("----------------------------filterChain-------------------------------");

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/login/**", "/sample/all").permitAll()
                    .requestMatchers("/sample/member").hasAnyRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()) // 기본 form 로그인 사용
                .logout(withDefaults());

        http.oauth2Login(withDefaults());

        return http.build();
    };

//    @Bean
//    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
//        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
//
//        return userRequest -> {
//            OAuth2User oAuth2User = delegate.loadUser(userRequest);
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oAuth2User.getAuthorities());
//
//            // Add the ROLE_USER authority
//            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//
//            // Return the OAuth2User with the additional role
//            return new CustomOAuth2User(oAuth2User, mappedAuthorities);
//        };
//    }
}
