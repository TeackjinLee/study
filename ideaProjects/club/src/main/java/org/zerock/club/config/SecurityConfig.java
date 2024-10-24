package org.zerock.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.zerock.club.security.handler.ClubLoginSuccessHandler;
import org.zerock.club.security.service.ClubUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Log4j2
public class SecurityConfig {

    @Autowired
    private ClubUserDetailsService userDetailsService;

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

        http.csrf(AbstractHttpConfigurer::disable);

        /*http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/login/**", "/sample/all").permitAll()
                .requestMatchers("/sample/member").hasAnyAuthority("OAUTH2_USER", "ROLE_USER")
                .anyRequest().authenticated()
        );*/

        http.formLogin(withDefaults()); // 기본 form 로그인 사용
        http.logout(withDefaults());

        http.oauth2Login(oauth2Login ->
                    oauth2Login.successHandler(clubLoginSuccessHandler())
                );

        // 자동로그인 기능
        http.rememberMe(remember -> remember
                .tokenValiditySeconds(60 * 60 * 24 * 7) // 7일지정
                .userDetailsService(userDetailsService)
        );

        return http.build();
    };

    @Bean
    public ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

}
